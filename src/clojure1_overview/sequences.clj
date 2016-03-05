(ns
  clojure1_overview.sequences
  (:use [clojure.repl :only (source)]))

; Sequences are logical views of collections. Many things can be treated as sequences.
; These include Java collections, Clojure-specific collections, strings, streams, directory structures and XML trees.

; Many Clojure functions return a lazy sequence. This is a sequence whose items can be the result of function calls
; that aren't evaluated until they are needed. A benefit of creating a lazy sequence is that it isn't necessary
; to anticipate how many items in it will actually be used at the time the sequence is created.

; Examples of functions and macros that return lazy sequences include:
;(source cache-seq)
;(source concat)
;(source cycle)
;(source distinct)
;(source drop)
;(source drop-last)
;(source drop-while)
;(source filter)
;(source for)
;(source interleave)
;(source interpose)
;(source iterate)
;(source lazy-cat)
;(source lazy-seq)
;(source line-seq)
;(source map)
;(source partition)
;(source range)
;(source re-seq)
;(source remove)
;(source repeat)
;(source replicate)
;(source take)
;(source take-nth)
;(source take-while)
;(source tree-seq)

; Lazy sequences are a common source of confusion for new Clojure developers.
; For example, what does the following code output?
(map #(println %) [1 2 3])
; When run in a REPL, this outputs the values 1, 2 and 3 on separate lines interspersed with a sequence of three nils
; which are the return values from three calls to the println function. The REPL always fully evaluates the results
; of the expressions that are entered. However, when run as part of a script, nothing is output by this code.
; This is because the `map` function returns a lazy sequence containing the results of applying its first argument
; function to each of the items in its second argument collection. The documentation string for the `map` function
; clearly states that it returns a lazy sequence.

; There are many ways to force the evaluation of items in a lazy sequence. Functions that extract a single item
; such as `first`, `second`, `nth` and `last` do this. The items in the sequence are evaluated in order,
; so items before the one requested are also evaluated. For example, requesting the last item causes every item
; to be evaluated.

;  If the head of a lazy sequence is held in a binding, once an item has been evaluated its value is cached
; so it isn't reevaluated if requested again.

; The `dorun` and `doall` functions force the evaluation of items in a single lazy sequence.
; The `doseq` macro, discussed in the "Iteration" section, forces the evaluation of items in one or more lazy sequences.
; The `for` macro, discussed in that same section, does not force evaluation and instead returns another lazy sequence.

; Using `doseq` or `dorun` is appropriate when the goal is to simply cause the side effects of the evaluations to occur.
; The results of the evaluations are not retained, so less memory is consumed. They both return nil.
; Using `doall` is appropriate when the evaluation results need to be retained. It holds the head of the sequence
; which causes the results to be cached and it returns the evaluated sequence.

; The table below illustrates the options for forcing the evaluation of items in a lazy sequence:

;+------------------------------------+----------------------------+----------------------------+
;|                                    | Retain evaluation results  | Discard evaluation results |
;+------------------------------------+----------------------------+----------------------------+
;| Operate on a single sequence       | doall                      | dorun                      |
;| and only cause side effects        |                            |                            |
;+------------------------------------+----------------------------+----------------------------+
;| Operate on any number of sequences |                            |                            |
;| with list comprehension syntax     | N/A                        | doseq                      |
;+------------------------------------+----------------------------+----------------------------+

; The `doseq` macro is typically preferred over the `dorun` function because the code is easier to read.
; It is also faster because a call to `map` inside `dorun` creates another sequence.
; For example, the following lines both produce the same output:
(time (dorun (map #(print %) [1 2 3])))  ; 123 "Elapsed time: 8.50704 msecs"
(time (doseq [i [1 2 3]] (print i)))     ; 123 "Elapsed time: 0.787601 msecs"

; If a function creates a lazy sequence that will have side effects when its items are evaluated, in most cases
; it should force the evaluation of the sequence with `doall` and return its result. This makes the timing of
; the side effects more predictable. Otherwise callers could evaluate the lazy sequence any number of times
; resulting in the side effects being repeated.

; The following expressions all output 123, but they have different return values.
; The `do` special form is used here to implement an anonymous function that does more than one thing,
; print the value passed in and return it.
(print "" (doseq [item [1 2 3]] (print item)) "\n")      ; 123 nil
(print "" (dorun (map #(print %) [1 2 3])) "\n")         ; 123 nil
(print "" (doall (map #(do (print %) %) [1 2 3])) "\n")  ; 123 (1 2 3)

; Lazy sequences make it possible to create infinite sequences since all the items don't need to be evaluated.
(defn sq-div2
  "square the argument and divide by 2"
  [x]
  (/ (* x x) 2.0))
; Create an infinite sequence of results from the function f
; for the values 0 through infinity.
; Note that the head of this sequence is being held in the binding "f-seq".
; This will cause the values of all evaluated items to be cached.
(def seq1 (map sq-div2 (iterate inc 0)))
; Force evaluation of the first item in the infinite sequence, (f 0).
(println "first is" (first seq1)) ; first is 0.0
; Force evaluation of the first three items in the infinite sequence.
; Since the (f 0) has already been evaluated,
; only (f 1) and (f 2) will be evaluated.
(println (doall (take 3 seq1))) ; (0.0 0.5 2.0)
; uses cached result:
(println (time (nth seq1 2)))  ; "Elapsed time: 0.027381 msecs" \n 2.0
; doesn't use cached result:
(println (time (nth seq1 5)))  ; "Elapsed time: 0.070429 msecs" \n 12.5

; Here's a variation on the previous code that does not hold the head of the lazy sequence in a binding.
; Note how the sequence is defined as the result of a function rather than the value of a binding.
; The results for evaluated items are not cached. This reduces memory requirements, but is less efficient
; when items are requested more than once.
(defn seq2 [] (map sq-div2 (iterate inc 0)))
; evaluates (sq-div2 0), but doesn't cache result:
(println (time (first (seq2))))   ; "Elapsed time: 0.098235 msecs" \n 0.0
; evaluates (sq-div2 0), (sq-div2 1) and (sq-div2 2):
(println (time (nth (seq2) 2)))   ; "Elapsed time: 3.315552 msecs" \n 2.0

; Another way to avoid holding the head of a lazy sequence in a binding is to pass the
; lazy sequence directly to a function that will evaluate its items.
(defn consumer [seq]
  ; Since seq is a local binding, the evaluated items in it
  ; are cached while in this function and then garbage collected.
  (println (first seq))   ; evaluates (f 0)
  (println (nth seq 2)))  ; ; evaluates (f 1) and (f 2)
(time (consumer (map sq-div2 (iterate inc 0)))) ; 0.0 \n 2.0 \n "Elapsed time: 3.084276 msecs"