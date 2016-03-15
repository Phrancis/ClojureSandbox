(ns
  ^{:author francisveilleux-gaboury}
  sandbox.HackerRankProjectEuler11)

;; Project Euler #11: Largest product in a grid
;; https://www.hackerrank.com/contests/projecteuler/challenges/euler011

(def grid-2x2 (vector 42 99 12 23))

(def grid-20x20
  (vector
    89 90 95 97 38 15  0 40  0 75  4  5  7 78 52 12 50 77 91  8
    49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48  4 56 62  0
    81 49 31 73 55 79 14 29 93 71 40 67 53 88 30  3 49 13 36 65
    52 70 95 23  4 60 11 42 69 24 68 56  1 32 56 71 37  2 36 91
    22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80
    24 47 32 60 99  3 45  2 44 75 33 53 78 36 84 20 35 17 12 50
    32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70 ;; 26 @ row 6 col 8  : ix 128
    67 26 20 68  2 62 12 20 95 63 94 39 63  8 40 91 66 49 94 21 ;; 63 @ row 7 col 9  : ix 149
    24 55 58  5 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72 ;; 78 @ row 8 col 10 : ix 170
    21 36 23  9 75  0 76 44 20 45 35 14  0 61 33 97 34 31 33 95 ;; 14 @ row 9 col 11 : ix 191
    78 17 53 28 22 75 31 67 15 94  3 80  4 62 16 14  9 53 56 92
    16 39  5 42 96 35 31 47 55 58 88 24  0 17 54 24 36 29 85 57
    86 56  0 48 35 71 89  7  5 44 44 37 44 60 21 58 51 54 17 58
    19 80 81 68  5 94 47 69 28 73 92 13 86 52 17 77  4 89 55 40
     4 52  8 83 97 35 99 16  7 97 57 32 16 26 26 79 33 27 98 66
    88 36 68 87 57 62 20 72  3 46 33 67 46 55 12 32 63 93 53 69
     4 42 16 73 38 25 39 11 24 94 72 18  8 46 29 32 40 62 76 36
    20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74  4 36 16
    20 73 35 29 78 31 90  1 74 31 49 71 48 86 81 16 23 57  5 54
     1 70 54 71 83 51 54 69 16 92 33 48 61 43 52  1 89 19 67 48))

(defn get-sqr-grid-sides
  [grid]
  "Given a 'square' grid, i.e., a grid whose number of
  horizontal items is equal to its number of vertical items,
  returns that number. Returns nil if grid is not 'square'.
  Ex1: (get-sqr-grid-sides [:a :b :c :d]) ;;=> 2)
  Ex2: (get-sqr-grid-sides [:a :b :c :d :e]) ;;=> nil"
  (let [N (Math/sqrt (count grid))]
    (if (== N (int N))
      (int N)
      nil)))

(defn with-indexes
  [coll]
  "Given a collection, returns a list of vectors each containing
  the index of each item (first) along with its value (second).
  Ex: (with-indexes [:a :b :c]) ;-> ([0 :a] [1 :b] [2 :c])"
  (map #(vector %1 %2) (range) coll))

(defn filter-between-indexes
  [coll ix-first ix-last]
  "Given a collection and a first and last index (zero-indexed),
  returns a list of the values between the indexes, inclusive.
  Ex: (filter-between-indexes [:a :b :c :d :e] 0 2) ;-> (:a :b :c)"
  (let [ix-coll (with-indexes coll)
        result (filter #(and (>= (first %) ix-first)
                             (<= (first %) ix-last)) ix-coll)]
    (map #(second %) result)))


(defn horizontal-sums
  [grid row num-items]
  (let [ix-start 0
        ix-end (- (get-sqr-grid-sides grid) (+ 1 num-items))]
    ;;(println ix-start ix-end)
    (vector (map ))
    ))


(defn -main
  [& args]
  ;; get horizontals
  ;(println (map #(get grid-20x20 %) (range 0 19 1)))
  ;; get verticals
  ;(println (map #(get grid-20x20 %) (range 0 400 20)))
  ;; get diagonals
  ;(println (map #(get grid-20x20 %) (range 0 400 21)))
  ;(println (map #(get grid-20x20 %) (range 0 400 19)))
  ;; grid size
  ;(println (get-sqr-grid-sides grid-20x20)) ;; 20
  ;(println (get-sqr-grid-sides [1 2 3 4 5])) ;; nil
  ;(println (with-indexes grid-2x2)) ;; OK
  ;(println (with-indexes grid-20x20)) ;; OK
  ;(println (filter-between-indexes grid-20x20 0 3))      ;; (89 90 95 97)
  ;(println (filter-between-indexes grid-20x20 380 399))  ;; (1 70 54 71 83 51 54 69 16 92 33 48 61 43 52 1 89 19 67 48)
  (println (map #(->> grid-20x20 (drop %) (take 20)) ))
  )

(-main)
