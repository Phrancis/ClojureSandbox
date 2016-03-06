(ns
  clojure1_overview.17b-Refs)

;Refs are used to ensure that changes to one or more bindings are coordinated between multiple threads. This coordination
; is implemented using Software Transactional Memory (STM). Refs can only be modified inside a transaction.
;
;STM has properties that are similar to database transactions. All changes made inside an STM transaction only become
; visible to other threads at a single point in time when the transaction commits. This makes them both atomic and isolated.
; Validation functions make it possible to insure that changes are consistent with the values of other data.
;
;Code to be executed inside a transaction appears inside the body of a call to the dosync macro which demarcates the
; transaction. While inside a transaction, Refs that are changed have a private, in-transaction value that is not seen
; by other threads until the transaction commits.
;
;If no exceptions are thrown before the end of the transaction is reached then changes to Refs made in the transaction
; are committed. This means the in-transaction changes become visible outside the transaction.
;
;If an exception is thrown from any code executed inside the transaction, including those thrown from validation functions,
; the transaction rolls back. This means the in-transaction changes are discarded.
;
;While in a transaction, if an attempt is made to read or modify a Ref that has been modified in another transaction
; that has committed since the current transaction started (a conflict), the current transaction will retry. This means
; it will discard all its in-transaction changes and return to the beginning of the dosync body. There are no guarantees
; about when a transaction will detect a conflict or when it will begin a retry, only that they will be detected and
; retries will be performed.
;
;It is important that the code executed inside transactions be free of side effects since it may be run multiple times
; due to these retries. One way to support calls to functions that have side effects is to make the calls in actions
; that are sent to agents from inside a transaction. Those are held until the transaction completes. If the transaction
; commits then the actions are sent once regardless of the number of retries that occur. If the transaction rolls back
; then the actions are not sent.

;The `ref` function creates a new Ref object.
; One way to create a Ref and retain access to it is to use the `def` special form.
  ;(def name (ref value))

;The dosync macro starts a transaction that continues while the expressions in its body are evaluated.
; The ref-set function changes the in-transaction value of a Ref and returns it. It must be called inside a transaction,
; otherwise an IllegalStateException is thrown. The change will only be visible outside the transaction if and when
; the transaction commits. This happens when a dosync exits without an exception being thrown. For example:
  ;(dosync
  ;  ...
  ;  (ref-set name new-value)
  ;  ...)

;If the new value must be computed from the old value then three steps are required.
;
;1) deference the Ref to get the old value
;2) compute the new value
;3) set the new value
;
;The alter and commute functions perform these three steps as a single operation. The alter function is used for changes
; that must be made in a specific order. The commute function is used for changes whose order is not important
; (i.e., commutative) and can be performed in parallel. Like ref-set, both must be called inside a transaction.
; Both take an "update function" that will compute the new value and additional arguments to be passed to it.
; This function will be passed the current in-transaction value of the Ref followed by the additional arguments, if any.
; Whenever the new value is based on the old value (computed as a function of the old value), using alter or commute
; is preferred over ref-set.
;
;For example, suppose we want to add one to the value of a Ref named counter. This could be implemented as follows,
; using inc for the update function:
;
  ;(dosync
  ;  ...
  ;  (alter counter inc)
  ;  ; or as
  ;  (commute counter inc)
  ;  ...)
;
;If an alter attempts to modify a Ref that has been changed by another thread since the current transaction began,
; the current transaction will retry from the beginning. Calls to commute do not do this. They proceed through the
; current transaction using in-transaction values for the Refs. This results in better performance because retries
; aren't performed. Remember though that commute should only be used when the order of the updates they make across
; multiple transactions are not important.
;
;If the transaction commits, something extra happens for the commute calls that were made in the transaction.
; For each commute call, the Ref they set will be reset using the result of the following call:
;
  ;(apply update-function last-committed-value-of-ref args)
;
;Note that the update function will be passed the last committed value of the Ref. This may be the result of a
; transaction that committed in another thread after the current transaction began. It is not passed the in-transaction
; value of the Ref from the current transaction.
;
;Using commute instead of alter is an optimization. It will not produce different end results unless the order of the
; updates really does matter.

; Let's walk through an example that uses both Refs and Atoms (which are explained in more detail later).
; This example involves bank accounts and their transactions. First we'll define the data model.

;-------

; Assume the only account data that can change is its balance.
(defstruct account-struct :id :owner :balance-ref)

; We need to be able to add and delete accounts to and from a map.
; We want it to be sorted so we can easily
; find the highest account number
; for the purpose of assigning the next one.
(def account-map-ref (ref (sorted-map)))

; The following function creates a new account saves it in the account map, and returns it.
(defn open-account
  "creates a new account, stores it in the account map and returns it"
  [owner]
  (dosync ; required because a Ref is being changed
    (let [account-map @account-map-ref
          last-entry (last account-map)
          ; The id for the new account is one higher than the last one.
          id (if last-entry (inc (key last-entry)) 1)
          ; Create the new account with a zero starting balance.
          account (struct account-struct id owner (ref 0))]
      ; Add the new account to the map of accounts.
      (alter account-map-ref assoc id account)
      ; Return the account that was just created.
      account)))

; The following functions support depositing and withdrawing money to and from an account.
(defn deposit
  "adds money to an account; can be a negative amount"
  [account amount]
  (dosync ; required because a Ref is being changed
    (Thread/sleep 50) ; simulate a long-running operation
    (let [owner (account :owner)
          balance-ref (account :balance-ref)
          type (if (pos? amount) "deposit" "withdraw")
          direction (if (pos? amount) "to" "from")
          abs-amount (Math/abs amount)]
      (if (>= (+ @balance-ref amount) 0) ; sufficient balance?
        (do
          (alter balance-ref + amount)
          (println (str type "ing") abs-amount direction owner))
        (throw (IllegalArgumentException.
                 (str "insufficient amount for " owner " to withdraw " abs-amount)))))))

(defn withdraw
  "removes money from account"
  [account amount]
  ; A withdrawal is like a negative deposit.
  (deposit account (- amount)))

;The following function supports transferring money from one account to another. The transaction started
; by `dosync` ensures that either both the withdrawal and the deposit occur or neither occurs.
(defn transfer
  [from-account to-account amount]
  (dosync
    (println "Transferring" amount "from" (from-account :owner) "to" (to-account :owner))
    (withdraw from-account amount)
    (deposit to-account amount)))

; The following functions support reporting on the state of accounts. The transaction started by dosync ensures
; that the report will be consistent across accounts. For example, it won't report balances that are the result
; of a half-completed transfer.
(defn- report-1 ; a private function
  "prints information about a single account"
  [account]
  ; This assumes it is being called from within
  ; the transaction started in report.
  (let [balance-ref (account :balance-ref)]
    (println "Balance for" (account :owner) "is" @balance-ref)))

(defn report
  "prints information about any number of accounts"
  [& accounts]
  (dosync (doseq [account accounts] (report-1 account))))

; This code doesn't handle exceptions that occur within the threads it starts.
; Instead we'll define an exception handler for them in the current thread.

; Set a default uncaught exception handler to handle exceptions not caught in other threads:
(Thread/setDefaultUncaughtExceptionHandler
  (proxy [Thread$UncaughtExceptionHandler] []
    (uncaughtException [thread throwable]
      ; Just print the message in the exception.
      (println (.. throwable .getCause .getMessage)))))

; Now we're ready to exercise the functions defined above.
(let [a1 (open-account "Mark")
      a2 (open-account "Tami")
      thread (Thread. #(transfer a1 a2 50))]
  (try
    (deposit a1 100)
    (deposit a2 200)
    ; There are sufficient funds in Mark's account at this point
    ; to transfer $50 to Tami's account.
    (.start thread) ; will sleep in deposit function twice!

    ; Unfortunately, due to the time it takes to complete the transfer
    ; (simulated with sleep calls), the next call will complete first.
    (withdraw a1 75)

    ; Now there are insufficient funds in Mark's account
    ; to complete the transfer.
    (.join thread)
    (report a1 a2)
    (catch IllegalArgumentException e
      (println (.getMessage e) "in main thread"))))

;depositing 100 to Mark
;depositing 200 to Tami
;Transferring 50 from Mark to Tami
;withdrawing 75 from Mark
;Transferring 50 from Mark to Tami
;
;Exception: java.lang.IllegalArgumentException thrown from the UncaughtExceptionHandler in thread "Thread-0"
;Balance for Mark is 25
;Balance for Tami is 200