(ns
  clojure1_overview.2--types-of-collections)

; Clojure has 4 core types of collections,

; VECTORS
; Vectors are ordered collections of items.
; - Best for adding/removing items from the back
; - Efficient for finding or changing items by index
; - Function and macro arguments are generally in the form of a vector
(def vector1 (vector "one" "two" "three" "four"))
(def vector2 ["one" "three" "two" "four"])

; LISTS
; Lists are unordered collections of items.
; - Best for adding/removing items from the front
; - Inefficient for finding or changing items by index
(def list1 (list "one" "two" "three" "four"))
(def list2 (quote ("one" "three" "two" "four")))
;; Note the single-quote syntax:
(def list3 '("four" "one" "two" "three"))


; SETS
; Sets are collections of unique items.
; - Clojure supports two kinds of sets, unsorted and sorted
; - Preferred over lists and vectors when duplicates are not allowed and items do not need
; to be maintained in the order in which they were added
(def unsorted-set1 (hash-set 1 4 2 3))
(def unsorted-set2 #{1 4 2 3})
;; THIS WILL CAUSE AN ERROR DUE TO DUPLICATE KEY:
;; (def unsorted-set3 (hash-set 1 1 4 4 2 2 3 3))
(def sorted-set1 (sorted-set 1 4 2 3))


; MAPS
; Maps store associations between keys and their corresponding values where both can be any kind of object.
; - Often keywords are used for map keys
; - Clojure supports two kinds of maps, unsorted and sorted
; - Commas are treated as white space but can help readability
(def map1 (hash-map :DC "Washington" :AZ "Phoenix" :CA "Sacramento"))
(def map2 {:DC "Washington" :AZ "Phoenix" :CA "Sacramento"})
(def sorted-map1 (sorted-map 3 "three" 1 "one" 2 "two"))

(defn -main []
  (println "vector1" vector1)
  (println "vector2" vector2)
  (println "list1" list1)
  (println "list2" list2)
  (println "list3" list3)
  (println "unsorted-set1" unsorted-set1)
  (println "unsorted-set2" unsorted-set2)
  (println "sorted-set1" sorted-set1)
  (println "map1" map1)
  (println "map2" map2)
  (println "sorted-map1" sorted-map1))
(-main)
