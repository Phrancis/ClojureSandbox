(ns
  clojure1_overview.collections-vectors)

; Vectors are ordered collections of items. They are ideal when new items will be added to or removed
; from the back (constant-time). This means that using conj is more efficient than cons for adding items.
; They are efficient (constant time) for finding (using nth) or changing (using assoc) items by index.
; Function definitions specify their parameter list using a vector.

; Here are some ways to create a vector:
(def nums (vector "one" "two" "three" "four"))
(def nums2 ["one" "two" "three" "four"])

; Unless the list characteristic of being more efficient at adding to or removing from the front is
; significant for a given use, vectors are typically preferred over lists.
; This is mainly due to the vector syntax of [...] being a bit more appealing than the list syntax of '(...).
; It doesn't have the possibility of being confused with a call to a function, macro or special form.

; The get function retrieves an item from a vector by index. As shown later, it also retrieves a value from a map by key.
; Indexes start from zero. The get function is similar to the nth function.
; Both take an optional value to be returned if the index is out of range.
; If this is not supplied and the index is out of range, get returns nil and nth throws an exception.
(get nums 0 "unknown") ; one
(get nums 1 "unknown") ; two
(get nums 4 "unknown") ; unknown
(get nums 4)           ; nil
(nth nums 0 "unknown") ; one
(nth nums 4 "unknown") ; unknown
;(nth nums 4)           ; IndexOutOfBoundsException

; The assoc function operates on vectors and maps.
; When applied to a vector, it creates a new vector where the item specified by an index is replaced.
; If the index is equal to the number of items in the vector, a new item is added to the end.
; If it is greater than the number of items in the vector, an IndexOutOfBoundsException is thrown.
(def ch [\A \B \C])
(println (assoc ch 0 \Z)) ; [Z B C]
(println (assoc ch 2 \Z)) ; [A B Z]
(println (assoc ch 3 \Z)) ; [A B C Z]
;(println (assoc ch 4 \Z)) ;IndexOutOfBoundsException