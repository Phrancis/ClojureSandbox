; ProjectEuler4: Largest palindrome product
; A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
; Find the largest palindrome made from the product of two 3-digit numbers.

(defn palindrome? [arg]
  "Returns true if arg is a palindrome, i.e., it reads the same both ways"
  (= (seq (str arg))
     (reverse (str arg))))

(defn pow [N X]
  "Returns N to the power of X"
  {:pre [(number? N) (number? X)]}
  (reduce * (repeat X N)))

(defn nums-with-n-digits [digits]
  "Return list of all numbers comprised of a certain number of digits"
  {:pre (number? digits)}
  (let [max (pow 10 digits)
        min (/ max 10)]
    (range min max)))

(defn products-of-nums-list [nums]
  "Returns a descending order list of distinct products of each of the numbers in nums"
  {:pre (list? nums)}
  (sort > (distinct (for [x nums y nums] (* x y)))))

(defn solve-pe4 [digits]
  "Returns list of solutions of largest palindrome products of all numbers with a certain number of digits"
  {:pre (number? digits)}
  (first (filter palindrome? (products-of-nums-list (nums-with-n-digits digits)))))

(defn -main []
  (println (solve-pe4 2)) ; 9009
  (println (solve-pe4 3))); correct answer

(-main)

