(ns
  clojure1_overview.add-test
  (:use clojure.test))

(defn add [x y]
  (+ x y))


(println (is (= 4 (add 2 2))))
  ;true
(println (is (= 5 (add 2 2))))
  ;expected: (= 5 (add 2 2))
  ;actual: (not (= 5 4))
  ;false
(println (are [x y] (= x y)
           (add 3 4) 7
           (add 2 2) 4
           (add 3 4) 8))
; Returns false for the whole function
;expected: (= (add 3 4) 8)
;actual: (not (= 7 8))
;false