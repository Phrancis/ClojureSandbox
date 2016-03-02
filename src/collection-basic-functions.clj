; The Clojure collection types have characteristics that differ from Java's collection types.
; All of them are immutable, heterogeneous and persistent.
; Being immutable means that their contents cannot be changed.
; Being heterogeneous means that they can hold any kind of object.
; Being persistent means that old versions of them are preserved when new versions are created.
; Clojure does this in a very efficient manner where new versions share memory with old versions.

(println "count:"
  (count [42 "hello" true])) ; 3
(println "reverse:"
  (reverse [42 "hello" true])) ; (true hello 42)

; This line uses an anonymous function that adds 3 to its argument.
(println "anonymous add 3:"
  (map #(+ % 3) [2 4 7 10 12])) ; (5 7 10)

; Adds corresponding items (does not add items which are not in all collections)
(println "add corresponding items:"
  (map + [2 4 7] [5 6] [1 2 3 4])) ; (8 12)

; The `apply` function returns the result of a given function
; when all the items in a given collection are used as arguments.
(println "apply add:" (apply + [2 4 7]))  ; 13
(println "apply sub:" (apply - [2 4 7]))  ; -9
(println "apply mult:" (apply * [2 4 7])) ; 56
(println "apply div:" (apply / [2 4 7]))  ; 1/14
(println)
; There are many functions that retrieve a single item from a collection.
; Note: examples below use a vector type of collection
(def numbers ["one" "two" "three" "four"])
(println "first:" (first numbers))    ; one
(println "second:" (second numbers))  ; two
(println "last:" (last numbers))      ; four
; Note: nth is zero-indexed
(println "nth 2:" (nth numbers 2))    ; three
; There are many functions that retrieve several items from a collection.
(println "next:" (next numbers)) ; (two three four)
(println "nthnext 2:" (nthnext numbers 2)) ; (three four)
(println "butlast:" (butlast numbers)) ; (one two three)
(println "drop-last 2:" (drop-last 2 numbers)) ; (one two)
; Get names containing more than three characters.
(println "filter >3 chars:" (filter #(> (count %) 3) numbers)) ; (three four)
; There are several predicate functions that test the items in a collection and have a boolean result.
; These "short-circuit" so they only evaluate as many items as necessary to determine their result.
(println "every? String:" (every? #(instance? String %) numbers)) ; true
(println "not-every? String:" (not-every? #(instance? String %) numbers)) ; false
(println "some Number:" (some #(instance? Number %) numbers)) ; nil
(println "not-any? Number:" (not-any? #(instance? Number %) numbers)) ; true