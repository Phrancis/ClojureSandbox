(ns project-euler.pe8
  (require [clojure.test :as test]) (:import (clojure.lang LazySeq)))

; Project Euler 8: Largest product in a series
; https://projecteuler.net/problem=8
; The four adjacent digits in the 1000-digit number that have the greatest product are 9 × 9 × 8 × 9 = 5832.
; Find the thirteen adjacent digits in the 1000-digit number that have the greatest product. What is the value of this product?

(def thousand-digits
  ; This is the 1000-digit number from the problem.
 "73167176531330624919225119674426574742355349194934
  96983520312774506326239578318016984801869478851843
  85861560789112949495459501737958331952853208805511
  12540698747158523863050715693290963295227443043557
  66896648950445244523161731856403098711121722383113
  62229893423380308135336276614282806444486645238749
  30358907296290491560440772390713810515859307960866
  70172427121883998797908792274921901699720888093776
  65727333001053367881220235421809751254540594752243
  52584907711670556013604839586446706324415722155397
  53697817977846174064955149290862569321978468622482
  83972241375657056057490261407972968652414535100474
  82166370484403199890008895243450658541227588666881
  16427171479924442928230863465674813919123162824586
  17866458359124566529476545682848912883142607690042
  24219022671055626321111109370544217506941658960408
  07198403850962455444362981230987879927244284909188
  84580156166097919133875499200524063689912560717606
  05886116467109405077541002256983155200055935729725
  71636269561882670428252483600823257530420752963450")

(defn string-to-num-seq
  "Returns a lazy sequence with only the numbers in string s."
  [s]
  {:pre [(string? s)]}
  (map read-string (re-seq #"[0-9]" s)))

(test/deftest string-to-num-seq-test
  (test/is (seq? (string-to-num-seq "123")))
  (test/is (instance? LazySeq (string-to-num-seq "123")))
  (test/is (= '(1 2 3) (string-to-num-seq "123")))
  (test/is (= '(1 2 3) (string-to-num-seq "1a2b3c")))
  (test/is (= '()      (string-to-num-seq "abc")))
  (test/is (thrown? AssertionError (string-to-num-seq nil))))

(defn multiply-seq
  "Returns product of all numbers in seq.
   Filters out non-number items from input.
   Seq with no numbers return 0."
  [xs]
  {:pre [(seq? xs)]}
  (let [nums (filter number? xs)]
  (cond
    (empty? nums) 0
    :else (apply * nums))))

(test/deftest multiply-seq-test
  ; Test standard usage:
  (test/is (number? (multiply-seq '(1 2 3))))
  (test/is (= 6 (multiply-seq '(1 2 3))))
  (test/is (= -6 (multiply-seq '(-1 2 3))))
  (test/is (= 0 (multiply-seq '())))
  ; Test that non-number items are filtered out:
  (test/is (= 6 (multiply-seq '(1 \A 2 "B" 3 :C 'D nil true false))))
  ; Test pre-conditions:
  (test/is (thrown? AssertionError (multiply-seq nil)))
  (test/is (thrown? AssertionError (multiply-seq 123)))
  (test/is (thrown? AssertionError (multiply-seq "123"))))

(defn solve-pe8
  "Returns largest product of n adjacent digits."
  [n digits-str]
  {:pre [(number? n)
         (string? digits-str)]}
  (apply max
    (map multiply-seq
      (partition n 1 (string-to-num-seq digits-str)))))

(test/deftest solve-pe8-test
  (test/is (number? (solve-pe8 4 "123456")))
  (test/is (= 360 (solve-pe8 4 "123456")))
  (test/is (= 5832 (solve-pe8 4 thousand-digits)))
  (test/is (= 23514624000 (solve-pe8 13 thousand-digits)))
  (test/is (thrown? AssertionError (solve-pe8 "4" "123456")))
  (test/is (thrown? AssertionError (solve-pe8 4 123456))))

(defn -main []
  (test/run-tests)
  (println (solve-pe8 4 "123456"))
  (println (solve-pe8 4 thousand-digits))
  (println (solve-pe8 13 thousand-digits))
)
(-main)