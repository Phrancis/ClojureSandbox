(ns
  clojure1_overview.2--types-of-collections)

; Clojure has 4 core types of collections,

; VECTORS
; Vectors are ordered collecions of items.
; - Best for adding/removing items from the back
; - Efficient for finding or changing items by index
; - Function and macro arguments are generally in the form of a vector
(def vector1 (vector "one" "two" "three" "four"))
(def vector2 ["one" "two" "three" "four"])

; LISTS
; Lists are unordered colletions of items.
; - Best for adding/removing items from the front
; - Inefficient for finding or changing items by index
(def list1 (list "one" "two" "three" "four"))
(def list2 (quote ("one" "two" "three" "four")))
(def list3 '("one" "two" "three" "four"))

; SETS
; Sets are collections of unique items.
; - Clojure supports two kinds of sets, unsorted and sorted
; - Preferred over lists and vectors when duplicates are not allowed and items do not need
; to be maintained in the order in which they were added
(def set1 (hash-set 1 4 2 3))
(def set2 #{1 4 2 3})
(def sorted-set1 (sorted-set 1 4 2 3))

; MAPS
; Maps store associations between keys and their corresponding values where both can be any kind of object.
; - Often keywords are used for map keys
; - Clojure supports two kinds of maps, unsorted and sorted
; - Commas are treated as white space but can help readability
(def map1 (hash-map :DC "Washington" :AZ "Phoenix" :CA "Sacramento"))
(def map2 {:DC "Washington" :AZ "Phoenix" :CA "Sacramento"})
(def sorted-map1 (sorted-map 1 "one" 2 "two" 3 "three"))
