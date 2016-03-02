; Lists are ordered collections of items. They are ideal when new items will be added to or removed from the front (constant-time).
; They are not efficient (linear time) for finding items by index (using nth)
; and there is no efficient way to change items by index.

; Here are some ways to create a list that all have the same result:
(def nums (list "one" "two" "three" "four"))
(def nums (quote ("one" "two" "three" "four")))
(def nums '("one" "two" "three" "four"))

; The `some` function can be used to determine if a collection contains a given item.
; It takes a predicate function and a collection.
; While it may seem tedious to need to specify a predicate function in order to test for the existence of a single item,
; it is somewhat intentional to discourage this usage.
; Searching a list for a single item is a linear operation.
; Using a set instead of a list is more efficient and easier.
