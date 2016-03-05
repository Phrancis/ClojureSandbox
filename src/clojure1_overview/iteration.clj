(ns
  clojure1_overview.iteration
  (:use [clojure.repl :only (source)]))

; There are many ways to "loop" or iterate through items in a sequence.
; The `dotimes` macro executes the expressions in its body a given number of times, assigning values from zero to one
; less than that number to a specified local binding. If the binding isn't needed (card-number in the example below),
; an underscore can be used as its placeholder.
(defn deal-cards [n]
  (dotimes [card-number n]
    (println "deal card number" (inc card-number))))
; (deal-cards 5)

; The while macro executes the expressions in its body while a test expression evaluates to true.
; The following example executes the while body while a given thread is still running:
(defn enter-sleep-leave [ms]
  "prints a message when entering, sleeps for N milliseconds,
  then prints another message when leaving the function"
  (println "entered fn" \newline)
  (Thread/sleep ms)
  (print \newline "leaving fn" \newline))

(defn while-alive [ms]
  "executes the `while` body while a given thread is still running"
  (let [thread (Thread. #(enter-sleep-leave ms))]
    (.start thread)
    (println "thread started" \newline)
    (while (.isAlive thread)
      (print \.)
      (flush))
    (println "thread stopped" \newline)))
; (while-alive 4)

;;; List Comprehension

; The `for` and `doseq` macros perform list comprehension. They support iterating through multiple collections
; (rightmost collection fastest) and optional filtering using `:when` and `:while` expressions. The `for` macro takes a
; single expression body and returns a lazy sequence of the results. The `doseq` macro takes a body containing any number
; of expressions, executes them for their side effects, and returns nil.

; The following examples both output names of some spreadsheet cells working down rows and then across columns.
; They skip the "B" column and only use rows that are less than 3. Note how the dorun function, described in the
; "Sequences" section, is used to force evaluation of the lazy sequence returned by the for macro.
(def cols "ABCD")
(def rows (range 1 4))

(println "`for` demo")
(dorun
  (for [col cols :when (not= col \B)
        row rows :while (< row 3)]
    (println (str col row))))

(println \newline "`doseq` demo")
(doseq [col cols :when (not= col \B)
        row rows :while (< row 3)]
  (println (str row col)))

(println \newline "this is like a `for each` loop, if no filter conditions are used - can also be done with `dorun` + `for`")
(doseq [col cols
        row rows]
  (println (str row col)))

; Expand a macro to its generated code:
;(println (macroexpand
;           '(doseq [col cols
;                    row rows]
;              (println (str row col)))
;           ))

; View source code of a fn or macro:
; PS: need this :use directive in `ns`:
;   (:use [clojure.repl :only (source)])
;(source doseq)
;(source for)