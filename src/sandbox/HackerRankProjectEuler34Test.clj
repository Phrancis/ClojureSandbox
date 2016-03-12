(ns
  ^{:author Phrancis}
  sandbox.HackerRankProjectEuler34Test
  (:require [clojure.test :as t])
  (:require sandbox.HackerRankProjectEuler34Test)
  (:use sandbox.HackerRankProjectEuler34))

(t/run-tests 'sandbox.HackerRankProjectEuler34Test)

(t/deftest test-throw-number-exc
  (t/is (thrown? IllegalArgumentException (throw-number-exc)))
  (t/is (thrown? IllegalArgumentException (throw-number-exc "foo")))
  (t/is (thrown? IllegalArgumentException (throw-number-exc "bar" "this is an error message: "))))

(t/deftest test-exponent
  (t/is (thrown? IllegalArgumentException (exponent 2 "foo")))
  (t/is (thrown? IllegalArgumentException (exponent "bar" 2)))
  (t/is (= 0 (exponent 0 0)))
  (t/is (= 0 (exponent 0 2)))
  (t/is (= 1 (exponent 2 0)))
  (t/is (= 2 (exponent 2 1)))
  (t/is (= 4 (exponent 2 2)))
  (t/is (= -2 (exponent -2 1)))
  (t/is (= 4 (exponent -2 2)))
  (t/is (= -8 (exponent -2 3)))
  (t/is (= 16 (exponent -2 4)))
  (t/is (= 1/2 (exponent 2 -1)))
  (t/is (= 1/4 (exponent 2 -2)))
  (t/is (= 1/125 (exponent 5 -3))))

(t/deftest test-factorial
  (t/is (thrown? IllegalArgumentException (factorial "foo")))
  (t/is (= 1 (factorial 0)))
  (t/is (= 1 (factorial 1)))
  (t/is (= 2 (factorial 2)))
  (t/is (= 6 (factorial 3)))
  (t/is (= 24 (factorial 4)))
  (t/is (= 120 (factorial 5)))
  (t/is (= 720 (factorial 6)))
  (t/is (= 5040 (factorial 7)))
  (t/is (= 40320 (factorial 8)))
  (t/is (= 362880 (factorial 9)))
  (t/is (= -1 (factorial -1)))
  (t/is (= -2 (factorial -2)))
  (t/is (= -6 (factorial -3)))
  (t/is (= -24 (factorial -4)))
  (t/is (= -120 (factorial -5)))
  (t/is (= -720 (factorial -6)))
  (t/is (= -5040 (factorial -7)))
  (t/is (= -40320 (factorial -8)))
  (t/is (= -362880 (factorial -9))))

(println (= \0 (get "01" 0)))

(t/deftest test-explode-num-to-digits
  (t/is (thrown? IllegalArgumentException (explode-num-to-digits "foo")))
  ;; verify that a collection of numbers passed as argument is *not* a number:
  (t/is (thrown? IllegalArgumentException (explode-num-to-digits (vector 1 2 3))))
  (t/is (thrown? IllegalArgumentException (explode-num-to-digits (list 1 2 3))))
  (t/is (thrown? IllegalArgumentException (explode-num-to-digits (hash-set 1 2 3))))
  (t/is (thrown? IllegalArgumentException (explode-num-to-digits (hash-map :1 1, :2 2, :3 3))))
  ;; standard cases:
  (t/is (= (list 0) (explode-num-to-digits 0)))
  (t/is (= (list 1 2 3) (explode-num-to-digits 123)))
  (t/is (= (list 3 2 1) (explode-num-to-digits 321)))
  ;; special cases to be considered as numbers, including 1 or more leading zeroes:
  (t/is (= (list 0 1) (explode-num-to-digits "01")))
  (t/is (= (list 0 0 7) (explode-num-to-digits "007")))
  (t/is (= (list 0 0 0 0 0 0 0) (explode-num-to-digits "0000000")))

  )