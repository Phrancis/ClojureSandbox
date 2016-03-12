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