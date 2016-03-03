(ns
  clojure1_overview.collections-lists)

; Lists are ordered collections of items. They are ideal when new items will be added to or removed from the front (constant-time).
; They are not efficient (linear time) for finding items by index (using nth)
; and there is no efficient way to change items by index.

; Here are some ways to create a list that all have the same result:
(def nums (list "one" "two" "three" "four"))
(def nums2 (quote ("one" "two" "three" "four")))
(def nums3 '("one" "two" "three" "four"))

; The `some` function can be used to determine if a collection contains a given item.
; It takes a predicate function and a collection.
; While it may seem tedious to need to specify a predicate function in order to test for the existence of a single item,
; it is somewhat intentional to discourage this usage.
; Searching a list for a single item is a linear operation.
; Using a set instead of a list is more efficient and easier.
(some #(= % "two") nums)    ; true
(some #(= % "hello") nums)  ; nil
; Another approach is to create a set from the list
; and then use the contains? function on the set as follows.
(contains? (set nums) "one") ; -> true

; While the `conj` function will create a new list, the `cons` function will create a new sequence.
; In each case the new item(s) are added to the front.
; The remove function creates a new list containing only the items for which a predicate function returns false.
(def more-nums (conj nums "zero"))          ; (zero one two three four)
(def less-nums (remove #(= % "four") nums)) ; (one two three)

; The `into` function creates a new list that contains all the items in two lists.
(def charz '(\A \B \C))
(def nums-charz (into nums charz)) ; (C B A one two three four)

