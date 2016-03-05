(ns
  clojure1_overview.14-namespaces
  (:import (java.text NumberFormat) (javax.swing JFrame JLabel)))

; CLOJURE CHEATSHEET!
;;; http://clojure.org/api/cheatsheet

; COMPLETE CLOJURE API, INCLUDING CONTRIB LIBRARIES:
;;; http://clojure.org/api/api

; LIST OF STANDARD CLOJURE NAMESPACES:
;;; http://clojure.github.io/clojure/


; Java groups methods in classes and classes in packages. Clojure groups things that are named by symbols
; in namespaces. These include Vars, Refs, Atoms, Agents, functions, macros and namespaces themselves.
;
; Symbols are used to assign names to functions, macros and bindings. Symbols are partitioned into
; namespaces. There is always a current default namespace, initially set to "user", and it is stored in the
; special symbol *ns*. The default namespace can be changed in two ways. The `in-ns` function merely changes it.
; The `ns` macro does that and much more. One extra thing it does is make all the symbols in the clojure.core
; namespace available in the new namespace (using `refer` which is described later). Other features of the
; `ns` macro are described later.
;
; The "user" namespace provides access to all the symbols in the clojure.core namespace. The same is true of
; any namespace that is made the default through use of the `ns` macro.
;
; In order to access items that are not in the default namespace they must be namespace-qualified. This is done
; by preceding a name with a namespace name and a slash. For example, the clojure.string library defines the
; `join` function. It creates a string by concatenating a given separator string between the string representation
; of all the items in a sequence. The namespace-qualified name of this function is `clojure.string/join`.

; The `require` function loads Clojure libraries. It takes one or more quoted namespace names. For example:
(require 'clojure.string)

; This merely loads the library. Names in it must still be namespace-qualified in order to refer to them.
; Note that namespace names are separated from a name with a slash, whereas Java package names are separated
; from a class name with a period.
(clojure.string/join "$" [1 2 3]) ; 1$2$3

; The `alias` function creates an alias for a namespace to reduce the amount of typing required to namespace-qualify symbols.
; Aliases are defined and only known within the current namespace.
(alias 'st 'clojure.string)
(st/join "$" [1 2 3]) ; 1$2$3

; The `refer` function makes all the symbols in a given namespace accessible in the current namespace without
; namespace-qualifying them. An exception is thrown if a name in the given namespace is already defined in the
; current namespace.
(require 'clojure.pprint)
(refer 'clojure.pprint)
; Now the code could be written as:
(pprint {:a 1 :b 2 :c 3})

; The combination of `require` and `refer` is used often, so the shortcut function `use` is provided to do both.
(use 'clojure.set)
(pprint (union #{:a :b :c} #{:c :d :e }))
(pprint (difference #{:a :b :c} #{:c :d :e}))
(pprint (intersection #{:a :b :c} #{:c :d :e}))

; The `ns` macro, mentioned earlier, changes the default namespace. It is typically used at the top of a source file.
; It supports the directives :require, :use and :import (for importing Java classes) that are alternatives to
; using their function forms. Using these is preferred over using their function forms.
; In the example below, note the use of :as to create an alias for a namespace.
; Also note the use of :only to load only part of a Clojure library.

;(ns com.ociweb.demo
;  (:require [clojure.string :as su])
;  ; assumes this dependency: [org.clojure/math.numeric-tower "0.0.1"]
;  (:use [clojure.math.numeric-tower :only (gcd, sqrt)])
;  (:import (java.text NumberFormat) (javax.swing JFrame JLabel)))
;
;(println (su/join "$" [1 2 3])) ; -> 1$2$3
;(println (gcd 27 72)) ; -> 9
;(println (sqrt 5)) ; -> 2.23606797749979
;(println (.format (NumberFormat/getInstance) Math/PI)) ; -> 3.142

(doto (JFrame. "Hello")
  (.add (JLabel. "Hello, World!"))
  (.pack)
  (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
  (.setVisible true))