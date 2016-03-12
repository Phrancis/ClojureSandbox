(ns
  ^{:author Phrancis}
  sandbox.HackerRankProjectEuler34)

;; HackerRank Project Euler #34: Digit factorials
;; https://www.hackerrank.com/contests/projecteuler/challenges/euler034

(defmacro throw-number-exc
  "Shortcut 'not a number' exception with optional argument"
  ([]
    `(throw (IllegalArgumentException. "Not a number")))
  ([arg]
    `(throw (IllegalArgumentException. (str "Not a number: " ~arg))))
  ([arg msg]
    `(throw (IllegalArgumentException. (str ~msg " " ~arg)))))

(defn exponent
  "Given a number N, returns N^x (i.e. N to the power of x)"
  [N x]
  (if (and (number? N) (number? x))
    (cond
      (= x 0) 1
      (= N 0) 0
      (> x 0) (reduce * (repeat x N))
      (< x 0) (/ 1 (exponent N (- x))))
    (if (number? x)
      (throw-number-exc N)
      (throw-number-exc x))))

(defn factorial
  "Given a number N, returns N! (i.e., N factorial)"
  [N]
  (if (number? N)
    (cond
      (= N 0) 1
      (>= N 1) (* N (factorial (- N 1)))
      (< N 0) (throw-number-exc N "Factorial for negative numbers is undefined: "))
    (throw-number-exc N)))

(defn explode-num-to-digits
  "Given a nmber N, returns a list of its separate digits."
  [N]
  (if (>= N 0)
    ;; Maps a lambda expr which converts a char to base-10 digit
    ;; to each char of a string representation of N.
    (map #(Character/digit % 10) (str N))
    (map #(Character/digit % 10) (str (- N)))))

(defn sum-of-factorials-of-digits
  "Given a number N, returns the sum of the factorials of each digit of N.
  Example: N = 35 -> 3! + 5! = 6 + 120 = 126"
  [N]
  (reduce + (map #(factorial %) (explode-num-to-digits N))))

(defn is-curious-number
  "A 'Curious Number' is a number where the sum of the factorial of each of its digits
  is evenly divisible by the number itself.
  For example 19 is a 'Curious Number': 1! + 9! = 1 + 362880 = 362881, and 362881 % 19 = 0."
  [N]
  (if (= (mod (sum-of-factorials-of-digits N) N) 0)
    N
    nil))

(defn list-all-curious-numbers-between
  "Given numbers min and max, return a list of all 'Curious Numbers' from min to max inclusive."
  [min max]
  (remove nil? (map #(when (is-curious-number %) %) (range min (+ max 1)))))

(defn sum-all-curious-numbers-up-to
  "Given a number N between 10 and 10^5, return the sum of a list of all 'Curious Numbers' 10 to N inclusive.
  This is as per constraint: 10 ≤ N ≤ 10^5 "
  [N]
  (if (and (>= N 10) (<= N (exponent 10 5)))
    (reduce + (list-all-curious-numbers-between 10 N))
    (throw-number-exc N "Number is not 10 <= N <= 10^5: ")))

(defn -main
  [& args]
  ;; benchmark tests
  (print "Curious Numbers to 10^2: ") (time (list-all-curious-numbers-between 10 (exponent 10 2)))
  (print "Curious Numbers to 10^3: ") (time (list-all-curious-numbers-between 10 (exponent 10 3)))
  (print "Curious Numbers to 10^4: ") (time (list-all-curious-numbers-between 10 (exponent 10 4)))
  (print "Curious Numbers to 10^5: ") (time (list-all-curious-numbers-between 10 (exponent 10 5)))
  (print "Sum Curious Numbers up to 10^2: " ) (time (sum-all-curious-numbers-up-to (exponent 10 2)))
  (print "Sum Curious Numbers up to 10^3: " ) (time (sum-all-curious-numbers-up-to (exponent 10 3)))
  (print "Sum Curious Numbers up to 10^4: " ) (time (sum-all-curious-numbers-up-to (exponent 10 4)))
  (print "Sum Curious Numbers up to 10^5: " ) (time (sum-all-curious-numbers-up-to (exponent 10 5))))

; (-main)
