(ns
  clojure1_overview.15-metadata
  (:use [clojure.repl :only (source)]))

; Clojure metadata is data attached to a symbol or collection that is not related to its logical value.
; Two objects that are logically equal, such as two cards that are both the king of clubs, can have different metadata.
; For example, metadata can be used to indicate whether a card is bent. For the purpose of most card games,
; the fact that a card is bent has no bearing on the value of the card.
(defstruct card-struct :rank :suit)
(def card1 (struct card-struct :king :club))
(def card2 (struct card-struct :king :club))

;(println (== card1 card2)) ; same identity? -> false
;(println (= card1 card2)) ; same value? -> true

(def card2 ^{:bent true} card2) ; adds metadata at read-time
(def card2 (with-meta card2 {:new false}))
(println (meta card1)) ; nil
(println (meta card2)) ; {:new false}
(println (= card1 card2)) ; true (still same value despite metadata diff)

;Some metadata names have a designated use in Clojure. :private has a boolean value that indicates whether
;access to a Var is restricted to the namespace in which it is defined. :doc is a documentation string for
;a Var. :test has a boolean value that indicates whether a function that takes no arguments is a test function.
;
;:tag is a string class name or a Class object that describes the Java type of a Var or the return type of
;a function. These are referred to as "type hints". Providing them can improve performance. To see where in
;your code Clojure is using reflection to determine types, and therefore taking a performance hit, set the
;global variable *warn-on-reflection* to true.
;
;Some metadata is automatically attached to Vars by the Clojure compiler. :file is the string name of the
;file that defines the Var.
;:line is the integer line number within the file where the Var is defined.
;:name is a Symbol that provides a name for the Var.
;:ns is a Namespace object that describes the namespace in which the Var is defined.
;:macro is a boolean that indicates whether a Var is a macro as opposed to a function or binding.
;:arglist is a list of vectors where each vector describes the names of the parameters a function accepts.
;Recall that a function can have more than one parameter list and body.
;
;Functions and macros, both represented by a Var object, have associated metadata. For example, enter the
;following in a REPL: (meta (var reverse)).
;The output will be similar to the following, but on a single line:

;{
;  :arglists ([coll]),
;  :doc Returns a seq of the items in coll in reverse order. Not lazy.,
;  :added 1.0,
;  :static true,
;  :line 910,
;  :column 1,
;  :file clojure/core.clj,
;  :name reverse,
;  :ns #object[clojure.lang.Namespace 0x63a12c68 clojure.core]
;}

;  The source macro, in the clojure.repl library, uses this metadata to retrieve the source code for a given function or macro.
(source reverse)
; PRINTS TO stdout:
;(defn reverse
;  "Returns a seq of the items in coll in reverse order. Not lazy."
;  {:added "1.0"
;   :static true}
;  [coll]
;  (reduce1 conj () coll))

(println (macroexpand '(and 2 3)))
; (let* [and__4233__auto__ 2] (if and__4233__auto__ (clojure.core/and 3) and__4233__auto__))
(source and)
;(defmacro and
;  "Evaluates exprs one at a time, from left to right. If a form
;  returns logical false (nil or false), and returns that value and
;  doesn't evaluate any of the other expressions, otherwise it returns
;  the value of the last expr. (and) returns true."
;  {:added "1.0"}
;  ([] true)
;  ([x] x)
;  ([x & next]
;    `(let [and# ~x]
;       (if and# (and ~@next) and#))))