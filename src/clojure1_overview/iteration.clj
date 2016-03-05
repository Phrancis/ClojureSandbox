(ns
  clojure1_overview.iteration)

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
  (println "entered fn" \newline)
  (Thread/sleep ms)
  (print \newline "leaving fn" \newline))

(defn while-alive [ms]
  (let [thread (Thread. #(enter-sleep-leave ms))]
    (.start thread)
    (println "thread started" \newline)
    (while (.isAlive thread)
      (print \.)
      (flush))
    (println "thread stopped" \newline)))
; (while-alive 4)

