(ns
  clojure1_overview.18-automated-testing
  (use [clojure.test]))

(run-all-tests)
;The primary automated testing framework for Clojure is the test library included in Clojure core. The following code demonstrates its main features.

; Tests can be written in separate functions
(deftest add-test
  ; The "is" macro takes a predicate, arguments to it,
  ; and an optional message.
  (is (= 4 (+ 2 2)))
  (is (= 2 (+ 2 0)) "adding zero doesn't change value"))

(deftest reverse-test
  (is (= [3 2 1] (reverse [1 2 3]))))

; Tests can verify that a specific exception is thrown.
(deftest division-test
  (is (thrown? ArithmeticException (/ 3.0 0))))

; The with-test macro can be used to add tests
; to the functions they test as metadata.
(with-test
  (defn add-vals
    [n1 n2]
    (+ n1 n2)
    (is (= 4 (add-vals 2 2)))
    (is (= 2 (add-vals 2 0)) "adding zero doesn't change value")
    (is (thrown? ClassCastException (add-vals "foo" "bar")))))

; The "are" macro takes a predicate template and
; multiple sets of arguments to it, but no message.
; Each set of arguments are substituted one at a time
; into the predicate template and evaluated.
(deftest multiply
  (are [n1 n2 result]
    (= (* n1 n2) result) ; a template
    1 1 1
    1 2 2
    2 3 6))

;To limit the depth of stack traces that are output when an exception is thrown from a test, bind the special symbol
; *stack-trace-depth* to an integer depth.
;
;When AOT compiling Clojure code to bytecode for production use, bind the *load-tests* symbol to false to avoid
; compiling test code.
;
;While not on the same level as automated testing, Clojure provides an `assert` macro. It takes an expression,
; evaluates it and throws an exception if it evaluates to false. This is useful for catching conditions that should
; never occur. For example:
(def day-of-week 8)
(is (thrown? AssertionError (assert (<= day-of-week 7))))