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

(t/deftest test-explode-num-to-digits
  (t/is (thrown? IllegalArgumentException (explode-num-to-digits "foo")))
  ;; standard cases:
  (t/is (= (list 0) (explode-num-to-digits 0)))
  (t/is (= (list 0) (explode-num-to-digits 0000)))
  (t/is (= (list 1 2 3) (explode-num-to-digits 123)))
  (t/is (= (list 3 2 1) (explode-num-to-digits 321)))
  (t/is (= (list 1 2 3) (explode-num-to-digits -123)))
  ;; special case strings to be considered as numbers, including 1 or more leading zeroes:
  (t/is (= (list 1 2 3) (explode-num-to-digits "123")))
  (t/is (= (list 1 2 3) (explode-num-to-digits "-123")))
  (t/is (= (list 0 1) (explode-num-to-digits "01")))
  (t/is (= (list 0 0 7) (explode-num-to-digits "007")))
  (t/is (= (list 0 0 0 0 0 0 0) (explode-num-to-digits "0000000"))))

(t/deftest test-sum-of-factorials-of-digits
  (t/is (thrown? IllegalArgumentException (sum-of-factorials-of-digits "foo")))
  (t/is (= 1 (sum-of-factorials-of-digits 0)))
  (t/is (= 24 (sum-of-factorials-of-digits 4)))
  (t/is (= 9 (sum-of-factorials-of-digits 123)))
  (t/is (= 33 (sum-of-factorials-of-digits 1234))))

;; Curious Numbers to 10^5: (19 56 71 93 145 219 758 768 7584 7684 9696 10081 21993 40585)

(t/deftest test-is-curious-number
  (t/is (thrown? IllegalArgumentException (is-curious-number "foo")))
  (t/is (false? (is-curious-number 10)))
  (t/is (= 19 (is-curious-number 19)))
  (t/is (false? (is-curious-number 20)))
  (t/is (= 56 (is-curious-number 56)))
  (t/is (false? (is-curious-number 57))))

(t/deftest test-list-all-curious-numbers-between
  (t/is (thrown? IllegalArgumentException (list-all-curious-numbers-between "foo" 100)))
  (t/is (thrown? IllegalArgumentException (list-all-curious-numbers-between 10 "bar")))
  (t/is (=
          (list 19 56 71 93 145 219 758 768 7584 7684 9696 10081 21993 40585)
          (list-all-curious-numbers-between 10 (exponent 10 5)))))

(t/deftest test-sum-all-curious-numbers-up-to
  (t/is (thrown? IllegalArgumentException (sum-all-curious-numbers-up-to "foo")))
  ;; test challenge constraint: 10 ≤ N ≤ 10^5
  (t/is (thrown? IllegalArgumentException (sum-all-curious-numbers-up-to 9)))
  (t/is (thrown? IllegalArgumentException (sum-all-curious-numbers-up-to (exponent 11 5))))
  ;; test values
  (t/is (= 19 (sum-all-curious-numbers-up-to 55)))
  (t/is (= 75 (sum-all-curious-numbers-up-to 70)))
  (t/is (= 146 (sum-all-curious-numbers-up-to 92)))
  (t/is (= 239 (sum-all-curious-numbers-up-to 144))))