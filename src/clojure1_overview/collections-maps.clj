; Maps store associations between keys and their corresponding values where both can be any kind of object.
; Often keywords are used for map keys.
; Entries can be stored in such a way that the pairs can be quickly retrieved in sorted order based on their keys.

; Here are some ways to create maps that store associations from popsicle colors to their flavors
; where the keys and values are both keywords.
; The commas aid readability. They are optional and are treated as whitespace.

; (keyword, keyword) hash-map
(def popsicle-map
  (hash-map :red :cherry, :green :apple, :purple :grape))   ; {:green :apple, :red :cherry, :purple :grape}
; (keyword, String) hash-map
(def popsicle-map2
  (hash-map :red "cherry", :green "apple", :purple "grape"))   ; {:green apple, :red cherry, :purple grape}
; (keyword, Number) hash-map
(def nums
  (hash-map :one 1 :two 2 :three 3)) ; {:one 1, :three 3, :two 2}
; (Number, keyword) hash-map
(def nums2
  (hash-map 1 :one 2 :two 3 :three)) ; {1 :one, 3 :three, 2 :two}

; (keyword, String) map
; Note: (order remains same as declared)
(def state-capitals
  {:DC "Washington"
   :AZ "Phoenix"
   :CA "Sacramento"
   :TX "Austin"
   :LA "Baton Rouge"
   :CO "Denver"}) ; {:DC Washington, :AZ Phoenix, :CA Sacramento, :TX Austin, :LA Baton Rouge, :CO Denver}

; (Number, char) sorted-map
(def alphabet
  (sorted-map 1 \A 3 \C 2 \B 4 \D)) ; {1 A, 2 B, 3 C, 4 D}

; Maps can be used as functions of their keys. Also, in some cases keys can be used as functions of maps.
; For example, keyword keys can, but string and integer keys cannot.
; The following are all valid ways to get the flavor of green popsicles, which is :apple
(get popsicle-map :green)
(popsicle-map :green)
(:green popsicle-map)

; The `contains?` function operates on sets and maps.
; When used on a map, it determines whether the map contains a given key.
; The `keys` function returns a sequence containing all the keys in a given map.
; The `vals` function returns a sequence containing all the values in a given map.
(contains? state-capitals :TX) ; true
(contains? state-capitals :NY) ; false
(keys state-capitals) ; (:DC :AZ :CA :TX :LA :CO)
(vals state-capitals) ; (Washington Phoenix Sacramento Austin Baton Rouge Denver)
(keys alphabet) ; (1 2 3 4)
(vals alphabet) ; (A B C D)

; The `assoc` function operates on maps and vectors.
; When applied to a map, it creates a new map where any number of key/value pairs are added.
; Values for existing keys are replaced by new values.
(assoc alphabet 1 \Z, 5 \E, 7 \G) ; {1 Z, 2 B, 3 C, 4 D, 5 E, 7 F}
(assoc popsicle-map :green :lime, :blue :blueberry) ; {:green :lime, :red :cherry, :blue :blueberry, :purple :grape}

; The `dissoc` function takes a map and any number of keys.
; It returns a new map where those keys are removed.
; Specified keys that aren't in the map are ignored.
(dissoc alphabet 2 6) ; {1 A, 3 C, 4 D}
(dissoc popsicle-map :red :yellow) ; {:green :apple, :purple :grape}

; When used in the context of a sequence, maps are treated like a sequence of clojure.lang.MapEntry objects.
; This can be combined with the use of `doseq` and destructuring, to easily iterate through all the keys and values.
; The following example iterates through all the key/value pairs in popsicle-map and binds the key to color
; and the value to flavor. The `name` function returns the string name of a keyword.
(doseq [[color flavor] popsicle-map]
  (println (str "The flavor of " (name color) " popsicles is " (name flavor) ".")))
;Prints:
;The flavor of green popsicles is apple.
;The flavor of red popsicles is cherry.
;The flavor of purple popsicles is grape.

; The select-keys function takes a map and a sequence of keys.
; It returns a new map where only those keys are in the map.
; Specified keys that aren't in the map are ignored.
(select-keys state-capitals [:DC :CA :NY]) ; {:DC Washington, :CA Sacramento}

; NESTED MAPS AND SOME ASSOCIATED FUNCTIONS

; Values in maps can be maps, and they can be nested to any depth.
; Retrieving nested values is easy. Likewise, creating new maps where nested values are modified is easy.
(def person {
  :name "Francis"
  :address {
    :street "123 Main St."
    :city "Louisville"
    :state :KY}
  :employer {
    :name "ABC Computing"
    :address {
      :street "456 Business Ln."
      :city "Dallas"
      :state :TX}}})
; The `get-in` function takes a map and a key sequence. It returns the value of the nested map key at the end of the sequence.
; The -> macro and the `reduce` function can also be used for this purpose.
; All of these are demonstrated below to retrieve the employer city which is "Dallas"
(get-in person [:employer :address :city])  ; Dallas

; The -> macro, referred to as the "thread" macro, calls a series of functions, passing the result of each as
; an argument to the next. For example the following lines have the same result:
; (f1 (f2 (f3 x)))
; (-> x f3 f2 f1)
; There is also a -?> macro in the clojure.core.incubator namespace that stops and returns nil if any function
; in the chain returns nil. This avoids getting a NullPointerException.
(-> person :employer :address :city)        ; Dallas

; The `reduce` function takes a function of two arguments, an optional value and a collection.
; It begins by calling the function with either the value and the first item in the collection or the
; first two items in the collection if the value is omitted.
; It then calls the function repeatedly with the previous function result and the next item in the collection
; until every item in the collection has been processed.
; This function is the same as `inject` in Ruby and `foldl` in Haskell.
(reduce get person [:employer :address :city]) ; Dallas

; The `assoc-in` function takes a map, a key sequence and a new value.
; It returns a new map where the nested map key at the end of the sequence has the new value.
; For example, a new map where the employer city is changed from "Dallas" to "Houston" can be created as follows:
(assoc-in person [:employer :address :city] "Houston")

; The `update-in` function takes a map, a key sequence, a function and any number of additional arguments.
; The function is passed the old value of the key at the end of the sequence and the additional arguments.
; The value it returns is used as the new value of that key.
; For example, a new map where the employer street is updated to add a suite number, e.g. "456 Business Ln. Suite 1000"
; instead of "456 Business Ln." can be created as follows:
(update-in person [:employer :address :street] str " Suite 1000")