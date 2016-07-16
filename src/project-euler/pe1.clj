; Project Euler 1: Multiples of 3 and 5
;If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
;Find the sum of all the multiples of 3 or 5 below 1000.

(defn is-multiple-of-3-or-5 [N]
  (zero?
    (min (mod N 3)
         (mod N 5))))

(defn solve-pe1 [max]
  (apply
    +
    (filter
      #(is-multiple-of-3-or-5 %)
      (range max))))

(defn -main []
  (println "Project Euler 1: Multiples of 3 and 5")
  (def i 10)
  (println "Solution of Project Euler 1 for" i "is:" (solve-pe1 i)) ; 23
  (def i 1000)
  (println "Solution of Project Euler 1 for" i "is:" (solve-pe1 i))  ; SEEKRIT
  )
(-main)