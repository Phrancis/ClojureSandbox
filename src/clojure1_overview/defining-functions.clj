; The `defn` macro defines a function. Its arguments are the function name, an optional documentation
; string (displayed by the `doc` macro), the parameter list (specified with a vector that can be empty) and the function body.
; The result of the last expression in the body is returned. Every function returns a value, but it may be nil.
(defn greetings
  "Returns a String greeting"
  [name]
  (str "Hello, " name))
(println "(greetings \"Clojure\") :" (greetings "Clojure")) ; Hello, World

; Function definitions must appear before their first use.
; Sometimes this isn't possible due to a set of functions that invoke each other.
; The `declare` special form takes any number of function names and creates forward declarations that resolve these cases.
; (declare function-names)

; Functions defined with the `defn-` macro are private. This means they are only visible in the namespace in which
; they are defined. Other macros that produce private definitions, such as `defmacro-`, are in clojure.core.incubator.

; Functions can take a variable number of parameters. Optional parameters must appear at the end.
; They are gathered into a list by adding an ampersand `&` and a name for the list at the end of the parameter list.
(defn power [base & exponents]
  "Calculate power using java.lang.Math static method pow"
  (reduce #(Math/pow %1 %2) base exponents))

(println "(power 2 7) :" (power 2 7))     ; 128.0
(println "(power 2 3 4) :" (power 2 3 4))   ; 4096.0 (2^3 = 8, 8^4 = 4096)
(println "(power 2 3 4 5) :" (power 2 3 4 5)) ; 1.15292150460684698E18 (2^3 = 8, 8^4 = 4096, 4096^5 = 1.15292150460684698E18)

; Function definitions can contain more than one parameter list and corresponding body.
; Each parameter list must contain a different number of parameters. This supports overloading functions based on arity.
; Often it is useful for a body to call the same function with a different number of arguments in order to
; provide default values for some of them.
(defn greeting-multilang
  "returns a String greeting in a given language"
  ([]                   ; 0 args
    (greeting-multilang "World"))
  ([name]               ; 1 arg
    (greeting-multilang name "en"))
  ([name language]      ; 2 args
  (condp = language     ; this is like a "case"/"switch" statement in other languages
    "en" (str "Hello, " name)
    "es" (str "Hola, " name)
    "sw" (str "Hallå, " name)
    (throw (IllegalArgumentException. (str "Unsupported language: " language))))))

(greeting-multilang)               ; Hello, World
(greeting-multilang "you")         ; Hello, you
(greeting-multilang "world" "en")  ; Hello, world
(greeting-multilang "mundo" "es")  ; Hola, mundo
(greeting-multilang "värld" "sw")  ; Hallå, värld
(try
  (greeting-multilang "Welt" "ge") ; this throws IllegalArgumentException
  (catch Exception e (println "Caught :" e))) ; Caught : #error { :cause Unsupported language: ge :via [{:type java.lang.IllegalArgumentException ... }

; Anonymous functions (a.k.a lambda expressions) have no name. These are often passed as arguments to a named function.
; They are handy for short function definitions that are only used in one place.
; There are two ways to define them:
(def years [1940 1944 1961 1985 1986])  ; both anonymous functions return (1940 1944 1986)
(filter (fn [year] (even? year)) years) ; long way w/ named arguments
(filter #(even? %) years)               ; short way where % refers to argument

