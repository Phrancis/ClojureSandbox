(ns
  clojure1_overview.Atoms)

;Atoms provide a mechanism for updating a single value that is far simpler than the combination of Refs and STM.
; They are not affected by transactions.
;There are three functions that change the value of an Atom, `reset!`, `compare-and-set!` and `swap!`.

;The `reset!` function takes the Atom to be set and the new value. It sets the new value without considering the current value.
(println "reset! test")
(def my-atom (atom 1))
(println @my-atom) ;-> 1
(reset! my-atom 2)
(println @my-atom) ;-> 2

;The `compare-and-set!` function takes the Atom to be set, what is believed to be the current value, and the desired
; new value. If the current value matches the second argument then it is changed to the new value and true is returned.
; Otherwise the value remains unchanged and false is returned. This is useful to avoid changing the value if some other
; code has changed it since it was dereferenced at a particular point.

;The `compare-and-set!` function is typically used at the end of a section of code where the beginning is a binding that
; captures the dereferenced value of the Atom. The code in between can assume one of two outcomes. One outcome is that
; the value of the Atom will remain unchanged while this code executes and `compare-and-set!` will change it at the end.
; The other outcome is that some other code will change the value of the Atom while this code executes and `compare-and-set!`
; will not change it at the end, returning false instead.
(println "compare-and-set! test")
(def my-atom (atom 1))

(defn update-atom []
  (let [curr-val @my-atom]
    (println "update-atom curr-val =" curr-val) ;-> 1
    (Thread/sleep 50) ; gives reset! time to run
    (println
      (compare-and-set! my-atom curr-val (inc curr-val))))) ;-> false

(let [thread (Thread. #(update-atom))]
  (.start thread)
  (Thread/sleep 25)  ; give thread time to call update-atom
  (reset! my-atom 3) ; happens after update-atom binds curr-val
  (.join thread))    ; wait for thread to finish

(println @my-atom) ;-> 3
; PRINTS
;compare-and-set! test
;update-atom curr-val = 1
;false
;3

;Why is the output from this code 3? The `update-atom` function is called in a separate thread before the `reset!` function.
; It captures the current value of the Atom which is 1. Then it sleeps to give the `reset!` function time to run. After
; that, the value of the Atom is 3. When the `update-atom` function calls `compare-and-set!` to increment the value,
; it fails because the current value is no longer 1. This means the value of the Atom remains set to 3.

;The `swap!` function takes an Atom to be set, a function to compute the new value and any number of additional arguments
; to be passed to the function. The function is called with the current value of the Atom and the additional arguments,
; if any. It is essentially a wrapper around the use of `compare-and-set!` with one important difference. It begins
; by dereferencing the Atom to save its current value. Next, it calls the function to compute the new value. Finally,
; it calls `compare-and-set!` using the current value obtained at the beginning. If `compare-and-set!` returns false,
; meaning the current value of the Atom didn't match its value before the call to the function, the function is called
; repeatedly until this check succeeds. This is the important difference.
;
; The previous code can be written using `swap!` instead of `compare-and-set!` as follows:
(println "swap! test")
(def my-atom (atom 1))

(defn update-atom [curr-val]
  (println "update-atom curr-val =" curr-val)
  (Thread/sleep 50) ; give reset! time to run
  (inc curr-val))

(let [thread (Thread. #(swap! my-atom update-atom))]
  (.start thread)
  (Thread/sleep 25) ; give swap! time to call update-atom
  (reset! my-atom 3)
  (.join thread))

(println @my-atom) ;-> 4
; PRINTS:
;swap! test
;update-atom curr-val = 1
;update-atom curr-val = 3
;4

;Why is the output from this code 4? The `swap!` function is called in a separate thread before the `reset!` function.
; When the update-atom function is called from `swap!`, the current value of the Atom is 1. However, due to the `sleep`
; call, it doesn't complete before `reset!` is run, which sets the value to 3. The `update-atom` function returns 2.
; Before `swap!` can set the Atom to 2 it needs to verify that its current value is still 1. It isn't, so `swap!`
; calls `update-atom` again. This time the current value is 3, so it increments that and returns 4. The `swap!`
; function now successfully verifies that it knew the current value before the last call to `update-atom` and it sets
; the Atom to 4.