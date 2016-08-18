(ns project-euler.pe4
    (require [clojure.string :as string]
             [clojure.test :as test]))

; ProjectEuler4: Largest palindrome product
; A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
; Find the largest palindrome made from the product of two 3-digit numbers.

(defn palindrome?
  "Returns true if x reads the same both ways. Returns false for nil input."
  [x]
  (let [s (str x)]
       (cond
         (nil? x) false
         :else (= s (string/reverse s)))))

(test/deftest palindrome?-test
  ; numbers
  (test/is (true? (palindrome? 303)))
  (test/is (false? (palindrome? 1234)))
  (test/is (true? (palindrome? 1)))
  ; strings
  (test/is (true? (palindrome? "ABBA")))
  (test/is (true? (palindrome? "XOX")))
  (test/is (false? (palindrome? "ABC")))
  (test/is (true? (palindrome? "")))
  (test/is (true? (palindrome? "    "))) ; even number of spaces
  (test/is (true? (palindrome? "   ")))  ; odd number of spaces
  ; others
  (test/is (false? (palindrome? nil))))

(defn make-pos
  "Makes n into a positive number if it is not already."
  [n]
  {:pre [(number? n)]}
  (if (neg? n) (- n) n))

(test/deftest make-pos-test
  (test/is (pos? (make-pos 42)))
  (test/is (pos? (make-pos -42)))
  (test/is (zero? (make-pos 0))))

(defn pow
  "Returns x to the power of y"
  [x y]
  {:pre [(number? x)
         (number? y)]}
  (let [result (reduce * (repeat (make-pos y) (make-pos x)))]
    (cond
      (zero? y) 1
      (and (neg? x) (neg? y)) (cond
                                (odd? y) (- (/ 1 result))
                                :else (/ 1 result))
      (neg? y) (/ 1 result)
      (neg? x) (- result)
      :else result)))

(test/deftest pow-test
  (test/is (= 4 (pow 2 2)))
  (test/is (= 100 (pow 10 2)))
  (test/is (= 1 (pow 2 0)))
  (test/is (= 1/4 (pow 2 -2)))
  (test/is (= -4 (pow -2 2)))
  (test/is (= -8 (pow -2 3)))
  (test/is (= 1/4 (pow -2 -2)))
  (test/is (= -1/8 (pow -2 -3))))

(defn nums-with-n-digits
  "Return a lazy sequence of all positive integers with n digits."
  [n]
  {:pre [(number? n)]}
  (let [end (pow 10 n)
        start (/ end 10)]
    (range start end)))

(test/deftest nums-with-n-digits-test
  (test/is (= 9 (count (nums-with-n-digits 1))))
  (test/is (= 1 (apply min (nums-with-n-digits 1))))
  (test/is (= 9 (apply max (nums-with-n-digits 1))))
  (test/is (= 90 (count (nums-with-n-digits 2))))
  (test/is (= 10 (apply min (nums-with-n-digits 2))))
  (test/is (= 99 (apply max (nums-with-n-digits 2)))))

(defn solve-pe4
  "Returns the largest palindrome made from the product of two n-digit numbers."
  [n]
  {:pre [(number? n)]}
  (->> (let [nums (nums-with-n-digits n)]
         ; `for` handles multiplying each of the numbers with each other
         ; to get the products of n-digit numbers.
         (for [x nums
               y nums]
           (* x y)))
    (filter palindrome?)
    (apply max)))

(test/deftest solve-pe4-test
  (test/is (= 9009 (solve-pe4 2)))
  (test/is (= 906609 (solve-pe4 3))))

(defn -main []
  (test/run-tests)
  (println (solve-pe4 2))   ; 9009
  (println (solve-pe4 3))   ; 906609
  ; (println (solve-pe4 4))  ; 99000099
  )

(-main)

