(ns
  clojure1_overview.2-collection-basic-functions)

; The Clojure collection types have characteristics that differ from Java's collection types.
; All of them are immutable, heterogeneous and persistent.
; Being immutable means that their contents cannot be changed.
; Being heterogeneous means that they can hold any kind of object.
; Being persistent means that old versions of them are preserved when new versions are created.
; Clojure does this in a very efficient manner where new versions share memory with old versions.

(count [42 "hello" true]) ; 3
(reverse [42 "hello" true]) ; (true hello 42)

; This line uses an anonymous function that adds 3 to its argument.
(map #(+ % 3) [2 4 7 10 12]) ; (5 7 10)

; Adds corresponding items (does not add items which are not in all collections)
(map + [2 4 7] [5 6] [1 2 3 4]) ; (8 12)

; The `apply` function returns the result of a given function
; when all the items in a given collection are used as arguments.
(apply + [2 4 7]) ; 13
(apply - [2 4 7]) ; -9
(apply * [2 4 7]) ; 56
(apply / [2 4 7]) ; 1/14

; There are many functions that retrieve a single item from a collection.
; Note: examples below use a vector type of collection
(def numbers ["one" "two" "three" "four"])
(first numbers)   ; one
(second numbers)  ; two
(last numbers)    ; four
; Note: nth is zero-indexed
(nth numbers 2)   ; three
; There are many functions that retrieve several items from a collection.
(next numbers)        ; (two three four)
(nthnext numbers 2)   ; (three four)
(butlast numbers)     ; (one two three)
(drop-last 2 numbers) ; (one two)
; Get names containing more than three characters.
(filter #(> (count %) 3) numbers) ; (three four)
; There are several predicate functions that test the items in a collection and have a boolean result.
; These "short-circuit" so they only evaluate as many items as necessary to determine their result.
(every? #(instance? String %) numbers)      ; true
(not-every? #(instance? String %) numbers)  ; false
(some #(instance? Number %) numbers)        ; nil
(not-any? #(instance? Number %) numbers)    ; true