(ns
  clojure1_overview.collections-structmaps)

; Note: StructMaps have been deprecated. Records are generally recommended instead.

; StructMaps are similar to regular maps, but are optimized to take advantage of common keys in multiple instances
; so they don't have to be repeated. Their use is similar to that of Java Beans.
; Proper equals and hashCode methods are generated for them.
; Accessor functions that are faster than ordinary map key lookups can easily be created.

; The `create-struct` function and `defstruct` macro, which uses `create-struct`, both define StructMaps.
; The keys are normally specified with keywords.
(def vehicle-struct (create-struct :make :model :year :color))  ; long way
(defstruct vehicle-struct :make :model :year :color)            ; short way

; The struct function creates an instance of a given StructMap. Values must be specified in the same order as
; their corresponding keys were specified when the StructMap was defined.
; Values for keys at the end can be omitted and their values will be nil. For example:
(def vehicle (struct vehicle-struct "Toyota"))                        ; {:make Toyota, :model nil, :year nil, :color nil}
(def vehicle (struct vehicle-struct "Toyota" "Corolla"))              ; {:make Toyota, :model Corolla, :year nil, :color nil}
(def vehicle (struct vehicle-struct "Toyota" "Corolla" 2015))         ; {:make Toyota, :model Corolla, :year 2015, :color nil}
(def vehicle (struct vehicle-struct "Toyota" "Corolla" 2015 "Black")) ; {:make Toyota, :model Corolla, :year 2015, :color Black}

; The accessor function creates a function for accessing the value of a given key
; in instances that avoids performing a hash map lookup. For example:
;   Note the use of `def` instead of `defn` because `accessor` returns
;   a function that is then bound to `make`.
(def make (accessor vehicle-struct :make))
(make vehicle)  ; Toyota
; without accessor function:
(vehicle :make) ; same but slower
(:make vehicle) ; same but slower

; New keys not specified when the StructMap was defined can be added to instances.
; However, keys specified when the StructMap was defined cannot be removed from instances.