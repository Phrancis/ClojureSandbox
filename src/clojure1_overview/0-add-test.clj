(ns
  clojure1_overview.0-add-test
  (:use clojure.test))

(defn add [x y]
  (+ x y))

; Individual test expressions:
(is (= 4 (add 2 2)))
  ;true
(is (= 5 (add 2 2)))
  ;FAIL in clojure.lang.PersistentList$EmptyList@1 (add-test.clj:11)
  ;expected: (= 5 (add 2 2))
  ;  actual: (not (= 5 4))

; Expression reports FAIL for any/every failed assertion, however the (are) form is evaluated as a whole
; and returns true or false apparently based solely on the last statement to be evaluated...

;Evaluates to FALSE:
(are [x y] (= x y)
  (add 3 4) 7
  (add 2 2) 4
  (add 3 4) 8)
  ;expected: (= (add 3 4) 8)
  ;  actual: (not (= 7 8))
  ;false
;Evaluates to TRUE:
(are [x y] (= x y)
  (add 3 4) 7
  (add 3 4) 8
  (add 2 2) 4)
  ;expected: (= (add 3 4) 8)
  ;  actual: (not (= 7 8))
  ;true