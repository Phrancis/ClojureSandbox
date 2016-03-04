(ns
  clojure1_overview.exception-handling)

; All exceptions thrown by Clojure code are runtime exceptions.
; Java methods invoked from Clojure code can still throw checked exceptions.
; The try, catch, finally and throw special forms provide functionality similar to their Java counterparts.

(defn collection? [obj]
  "Prints object class to stdout then verifies if coll is either
  a Clojure collection, or a Java Collection."
  (println "obj is a" (class obj))
  (or
    (coll? obj)
    (instance? java.util.Collection obj)))

(defn average [coll]
  "Verify if coll is a valid, non-empty collection,
  then apply the + function to all items in coll and
  divide by the number of items in it to find the average."
  (when-not (collection? coll)
    (throw (IllegalArgumentException. "expected a collection")))
  (when (empty? coll)
    (throw (IllegalArgumentException. "collection is empty")))
  (let [sum (apply + coll)]
    (/ sum (count coll))))

(try
  (println "list average ="   (average '(2 3) ))
  (println "vector average =" (average [2 3]  ))
  (println "set average ="    (average #{2 3} ))
  (let [arr-list (java.util.ArrayList.)]
    (doto arr-list (.add 2) (.add 3))
    (println "ArrayList average =" (average arr-list)))
  (println "string average =" (average "2 3"))
  (catch IllegalArgumentException e
    (println e)
    ; (.printStackTrace e) ; uncomment if stack trace is desired
    )
  (finally
    (println "in `finally`")))

;obj is a clojure.lang.PersistentList
;list average = 5/2
;obj is a clojure.lang.PersistentVector
;vector average = 5/2
;obj is a clojure.lang.PersistentHashSet
;set average = 5/2
;obj is a java.util.ArrayList
;ArrayList average = 5/2
;obj is a java.lang.String
;#error {
;         :cause expected a collection
;         :via
;         [{:type java.lang.IllegalArgumentException
;           :message expected a collection
;           :at [clojure1_overview.exception_handling$average invoke exception-handling.clj 19]}]obj is a clojure.lang.PersistentList
;         list average = 5/2
;         obj is a clojure.lang.PersistentVector
;         vector average = 5/2
;         obj is a clojure.lang.PersistentHashSet
;         set average = 5/2
;         obj is a java.util.ArrayList
;         ArrayList average = 5/2
;         obj is a java.lang.String
;         #error {
;                  :cause expected a collection
;                  :via
;                  [{:type java.lang.IllegalArgumentException
;                    :message expected a collection
;                    :at [clojure1_overview.exception_handling$average invoke exception-handling.clj 19]}]
; <STACK TRACE BELOW THIS>
;
; [...]
;
; in `finally`