(ns
  clojure1_overview.5-threads)

; All Clojure functions implement both the java.lang.Runnable interface and the java.util.concurrent.Callable interface.
; This makes it easy to execute them in new Java threads. For example:
(defn delayed-print [ms text]
  (Thread/sleep ms)
  (println text))

; Pass an anonymous function that invokes delayed-print
; to the Thread constructor so the delayed-print function
; executes inside the Thread instead of
; while the Thread object is being created.

(.start (Thread. #(delayed-print 1000 ", World!"))) ; prints 2nd (1000 ms later)
(print "Hello") ; prints 1st

; Output: Hello, World!