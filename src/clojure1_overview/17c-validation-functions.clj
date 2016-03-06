(ns
  clojure1_overview.17c-validation-functions)

; Before moving on to discuss the next reference type, here's an example of using a validation function to verify
; that all values assigned to a Ref are integers.

; Note the use of the :validator directive when creating the Ref
; to assign a validation function which is integer? in this case.

(def my-ref (ref 0 :validator integer?))

(try
  (dosync
    (ref-set my-ref 1) ; works
    ; The next line doesn't work, so the transaction is rolled back
    ; and the previous change isn't committed.
    (ref-set my-ref "foo"))
  (catch IllegalStateException e
    ;do nothing
    ))

(println "my-ref =" @my-ref) ; due to validation failure -> 0