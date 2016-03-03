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
;(try
;  (greeting-multilang "Welt" "ge") ; this throws IllegalArgumentException
;  (catch Exception e (println "Caught :" e))) ; Caught : #error { :cause Unsupported language: ge :via [{:type java.lang.IllegalArgumentException ... }

; Anonymous functions (a.k.a lambda expressions) have no name.
; These are often passed as arguments to a named function.
; They are handy for short function definitions that are only used in one place.
; There are two ways to define them:
(def years [1940 1944 1961 1985 1986])  ; both anonymous functions return (1940 1944 1986)
(filter (fn [year] (even? year)) years) ; long way w/ named arguments
(filter #(even? %) years)               ; short way where % refers to argument

; When an anonymous function is defined using the `fn` special form, the body can contain any number of expressions.
; It can also have a name (following "fn") which makes it no longer anonymous and enables it to call itself recursively.
; When an anonymous function is defined in the short way using #(...), it can only contain a single expression.
; To use more than one expression, wrap them in the `do` special form.
; If there is only one parameter, it can be referred to with %.
; If there are multiple parameters, they are referred to with %1, %2 and so on.
(defn pair-test [test-fn n1 n2]
  (if (test-fn n1 n2) "pass" "fail"))
; Use a test-fn that determines...
; 1-whether the sum of its two arguments is an even number:
(println (pair-test #(even? (+ %1 %2)) 3 5))  ;pass
; 2-whether the difference of its two arguments is a positive number:
(println (pair-test  #(pos? (- %1 %2))  3  5  ))   ;fail
;         ^.call..^  ^.test-fn.%1.%2.^  n1 n2
; Same as above but long form:
(println (pair-test (fn [n1 n2] (even? (+ n1 n2))) 3 5)) ;pass
(println (pair-test (fn [n1 n2] (pos? (- n1 n2))) 3 5))  ;fail

; Java methods can be overloaded based on parameter types. Clojure functions can only be overloaded on arity.
; Clojure multimethods however, can be overloaded based on anything.

; The `defmulti` and `defmethod` macros are used together to define a multimethod. The arguments to `defmulti` are
; the method name and the dispatch function which returns a value that will be used to select a method.
; The arguments to `defmethod` are the method name, the dispatch value that triggers use of the method,
; the parameter list and the body. The special dispatch value `:default` is used to designate a method to be used
; when none of the others match. Each defmethod for the same multimethod name must take the same number of arguments.
; The arguments passed to a multimethod are passed to the dispatch function.

; Here's an example of a multimethod that overloads based on type.
(println "** multimethod `what-am-i` dispatches `class` **")
(defmulti what-am-i class) ; `class` is the dispatch function
(defmethod what-am-i Number [arg] (println arg "is a Number"))
(defmethod what-am-i String [arg] (println arg "is a String"))
(defmethod what-am-i :default [arg] (println arg "is something else"))

(what-am-i 42)      ;42 is a Number
(what-am-i "hello") ;hello is a String
(what-am-i \A)      ;A is something else

; Since the dispatch function can be any function, including one you write, the possibilities are endless.
; For example, a custom dispatch function could examine its arguments and return a keyword to indicate
; a size such as :small, :medium or :large. One method for each size keyword can provide logic
; that is specific to a given size.
(defn eval-num-size [n]
  (cond
    (<= n 3) :small
    (<= n 10) :medium
    :default :large))
(println "** multimethod `what-size` dispatches `eval-num-size` **")
(defmulti what-size eval-num-size)
(defmethod what-size :small [n] (println n "is small"))
(defmethod what-size :medium [n] (println n "is medium"))
(defmethod what-size :large [n] (println n "is large"))
; test
(what-size 2)   ;2 is small
(what-size 6)   ;6 is medium
(what-size 12)  ;12 is large

; Underscores can be used as placeholders for function parameters that won't be used and therefore don't need a name.
; This is often useful in callback functions which are passed to another function so they can be invoked later.
; A particular callback function may not use all the arguments that are passed to it.
(defn callback1 [n1 n2 n3] (+ n1 n2 n3))  ; uses all 3 args
(defn callback2 [n1 _ n3] (+ n1 n3))      ; uses 1st and 3rd args
(defn caller [callback value]
  (callback (+ value 1) (+ value 2) (+ value 3)))
; test
(caller callback1 10) ;36 -> 11 + 12 + 13
(caller callback2 10) ;24 -> 11 + 13