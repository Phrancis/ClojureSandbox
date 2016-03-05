(ns
  clojure1_overview.recursion
  (:use [clojure.repl :only (source)]))

; Recursion occurs when a function invokes itself either directly or indirectly through another function that it calls.
; Common ways in which recursion is terminated include checking for a collection of items to become empty or
; checking for a number to reach a specific value such as zero. The former case is often implemented by successively
; using the `next` function to process all but the first item. The latter case is often implemented by decrementing
; a number with the `dec` function.

; Recursive calls can result in running out of memory if the call stack becomes too deep. Some programming languages
; address this by supporting "tail call optimization" (TCO). Java doesn't currently support TCO and neither does Clojure.
; One way to avoid this issue in Clojure is to use the `loop` and `recur` special forms.
; Another way is to use the `trampoline` function.

; The `loop/recur` idiom turns what looks like a recursive call into a loop that doesn't consume stack space.
; The `loop` special form is like the `let` special form in that they both establish local bindings, but it also
; establishes a recursion point that is the target of calls to recur.
; The bindings specified by `loop`  provide initial values for the local bindings. Calls to `recur` cause control
; to return to the `loop` and assign new values to its local bindings. The number of arguments passed to `recur`
; must match the number of bindings in the `loop`. Also, `recur` can only appear as the last call in the `loop`.

(defn factorial-1
  "computes the factorial of a positive integer
   in a way that doesn't consume stack space"
  [number]
  (loop [n number
         factorial 1]
    (if (zero? n)
      factorial
      (recur (dec n) (* factorial n)))))
(println (time (factorial-1 0)))    ; "Elapsed time: 0.050966 msecs" \n 1
(println (time (factorial-1 5)))    ; "Elapsed time: 0.047235 msecs" \n 120
(println (time (factorial-1 10)))   ; "Elapsed time: 0.041133 msecs" \n 3628800
(println (time (factorial-1 20)))   ; "Elapsed time: 0.053947 msecs" \n 2432902008176640000

; The `defn` macro, like the `loop` special form, establishes a recursion point. The `recur` special form
; can also be used as the last call in a function to return to the beginning of that function with new arguments.

; Another way to implement the factorial function is to use the `reduce` function. This was described back in
; the "Collections" section. It supports a more functional, less imperative style. Unfortunately, in this case,
; it is less efficient.
; Note that the `range` function takes a lower bound that is inclusive and an upper bound that is exclusive.

(defn factorial-2 [number]
  (reduce * (range 2 (inc number))))
(println (time (factorial-2 0)))    ; "Elapsed time: 6.213526 msecs" \n 1
(println (time (factorial-2 5)))    ; "Elapsed time: 0.099478 msecs" \n 120
(println (time (factorial-2 10)))   ; "Elapsed time: 0.050454 msecs" \n 3628800
(println (time (factorial-2 20)))   ; "Elapsed time: 0.059559 msecs" \n 2432902008176640000

; The same result can be obtained by replacing `reduce` with `apply`, but that takes even longer.
; This illustrates the importance of understanding the characteristics of functions when choosing between them.

; The recur special form isn't suitable for mutual recursion where a function calls another function which
; calls back to the original function. The `trampoline` function, not covered here, does support mutual recursion.