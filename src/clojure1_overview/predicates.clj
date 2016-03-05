(ns
  clojure1_overview.predicates
  (:use [clojure.repl :only (source)]))

; Clojure provides many functions that act as predicates, used to test a condition. They return a value that can be
; interpreted as true or false.
; - The values false and nil are interpreted as false.
; - The value true and every other value, including zero, are interpreted as true.
; Predicate functions usually have a name that ends in a question mark.

; Reflection involves obtaining information about an object other than its value, such as its type.
; There are many predicate functions that perform reflection.

; Predicate functions that test the type of a single object include:
;(source class?)
;(source coll?)
;(source decimal?)
;(source delay?)
;(source float?)
;(source fn?)
;(source instance?)
;(source integer?)
;(source isa?)
;(source keyword?)
;(source list?)
;(source macro?)
;(source map?)
;(source number?)
;(source seq?)
;(source set?)
;(source string?)
;(source vector?)

; Some non-predicate functions that perform reflection include:
;(source ancestors)
;(source bases)
;(source class)
;(source ns-publics)
;(source parents)

; Predicate functions that test relationships between values include:
;(source <)
;(source <=)
;(source =)
;(source not=)
;(source ==)
;(source >)
;(source >=)
;(source compare)
;(source distinct?)
;(source identical?)

; Predicate functions that test logical relationships include:
;(source and)
;(source or)
;(source not)
;(source true?)
;(source false?)
;(source nil?)

; Predicate functions that test sequences include:
;(source empty?)
;(source not-empty)
;(source every?)
;(source not-every?)
;(source some)
;(source not-any?)

; Predicate functions that test numbers include:
;(source even?)
;(source neg?)
;(source odd?)
;(source pos?)
;(source zero?)