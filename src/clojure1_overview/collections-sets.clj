(ns
  clojure1_overview.collections-sets)

; Sets are collections of unique items.
; They are preferred over lists and vectors when duplicates are not allowed and items do not need
; to be maintained in the order in which they were added.
; Clojure supports two kinds of sets, unsorted and sorted.
; If the items being added to a sorted set can't be compared to each other, a ClassCastException is thrown.

; Here are some ways to create a set:
(def int-list (hash-set 1 4 2 3))    ; not sorted #{1 4 3 2}
(def int-list2 #{1 4 2 3})           ; not sorted #{1 4 3 2}
(def int-list3 (sorted-set 1 4 2 3)) ; sorted #{1 2 3 4}

; The `contains?` function/predicate operates on sets and maps.
; When used on a set, it determines whether the set contains a given item.
; This is much simpler than using the `some` function which is needed to test this with a list or vector.
(def ch #{\A \B \C})
(contains? ch \A) ; true
(contains? ch \X) ; false

; Sets can be used as functions of their items. When used in this way, they return the item or nil.
; This provides an even more compact way to test whether a set contains a given item.
(ch \A) ; A
(ch \X) ; nil

; The `conj` and `into` functions also work with sets.
; The location where the items are added is only defined for sorted sets.
(def more-ch (conj ch \D)) ; #{A B C D}
(def less-ch (disj ch \B)) ; #{A C}