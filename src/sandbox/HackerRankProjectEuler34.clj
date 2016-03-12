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
      (= N 0) 0
      (= x 0) 1
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
      (<= N -1) (- (* (- N) (factorial (- (- N) 1)))))
    (throw-number-exc N)))

(defn explode-num-to-digits
  "Given an integer N, returns a list of its separate digits."
  [N]
  (if (number? N)
    ;; Maps a lambda expr which converts a char to base-10 digit
    ;; to each char of a string representation of N.
    (map #(Character/digit % 10) (str N))
    ;; Special case where N is a number starting with 0, represented as a string
    ;; examples: "01", "007"
    (if (and (string? N) (= \0 (get N 0)))
      ;; allow leading 0s to be included in the list as part of the 'number' which was 'exploded'
      (map #(Character/digit % 10) N)
      (throw-number-exc N))))



(defn sum-of-factorials-of-digits
  "Given a number N, returns the sum of the factorials of each digit of N.
  Example: N = 35 -> 3! + 5! = 6 + 120 = 126"
  [N]
  (if (number? N)
    (reduce + (map #(factorial %) (explode-num-to-digits N)))
    (throw-number-exc N)))

(defn is-curious-number
  "A 'Curious Number' is a number where the sum of the factorial of each of its digits
  is evenly divisible by the number itself.
  For example 19 is a 'Curious Number': 1! + 9! = 1 + 362880 = 362881, and 362881 % 19 = 0."
  [N]
  (if (number? N)
    (if (= (mod (sum-of-factorials-of-digits N) N) 0)
      N
      false)
    (throw-number-exc N)))

(defn list-all-curious-numbers-between
  "Given numbers min and max, return a list of all 'Curious Numbers' from min to max inclusive."
  [min max]
  (remove nil? (map #(when (is-curious-number %) %) (range min (+ max 1)))))

(defn sum-all-curious-numbers-up-to
  "Given a number N between 10 and 10^5, return the sum of a list of all 'Curious Numbers' 10 to N inclusive.
  This is as per constraint: 10 ≤ N ≤ 10^5 "
  [N]
  (if (number? N)
    (if (and (>= N 10) (<= N (exponent 10 5)))
      (reduce + (list-all-curious-numbers-between 10 N))
      (throw-number-exc N "Number is not 10 <= N <= 10^5: "))
    (throw-number-exc N)))

(defn -main
  [& args]
  ;; benchmark tests
;  (time (println "Curious Numbers to 10^2:" (list-all-curious-numbers-between 10 (exponent 10 2))))
;  (time (println "Curious Numbers to 10^3:" (list-all-curious-numbers-between 10 (exponent 10 3))))
;  (time (println "Curious Numbers to 10^4:" (list-all-curious-numbers-between 10 (exponent 10 4))))
;  (time (println "Curious Numbers to 10^5:" (list-all-curious-numbers-between 10 (exponent 10 5))))
;  (time (println "Curious Numbers to 10^6:" (list-all-curious-numbers-between 10 (exponent 10 6))))
  (time (println (try (sum-all-curious-numbers-up-to 5) (catch Exception e (println e)))))
  (time (println (try (sum-all-curious-numbers-up-to (exponent 11 5)) (catch Exception e (println e)))))
  (time (println (sum-all-curious-numbers-up-to 20)))
  (time (println (sum-all-curious-numbers-up-to (exponent 10 2))))
  (time (println (sum-all-curious-numbers-up-to (exponent 10 3))))
  (time (println (sum-all-curious-numbers-up-to (exponent 10 4))))
  (time (println (sum-all-curious-numbers-up-to (exponent 10 5))))
  )

;;(-main)
