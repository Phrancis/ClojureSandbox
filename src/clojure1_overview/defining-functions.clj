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

; The `complement` function returns a new function that is just like a given function,
; but returns the opposite logical truth value.
(defn teenager? [age] (and (>= age 13) (< age 20)))
(def non-teen? (complement teenager?))
(teenager? 15) ;true
(non-teen? 15) ;false
(non-teen? 20) ;true

; The `comp` function composes a new function by combining any number of existing ones. They are called from right to left.
(defn times2 [n] (* n 2))
(defn minus3 [n] (- n 3))
; Note the use of `def` instead of `defn` because comp returns a function that is then bound to "my-composition":
(def my-composition (comp minus3 times2))
(my-composition 4) ; 4 * 2 - 3 -> 5

; The partial function creates a new function from an existing one so that it provides fixed values for initial
; parameters and calls the original function. This is called a "partial application". For example, * is a function
; that takes any number of arguments and multiplies them together. Suppose we want a new version of that function
; that always multiplies by two.
; Note the use of `def` instead of `defn` because partial returns a function that is then bound to "times2".
(def times2 (partial * 2))
(times2 3 4) ; 2 * 3 * 4 -> 24

; **********
; Here's an interesting use of both the `map` and `partial` functions. We'll define functions that use the `map` function
; to compute the value of an arbitrary polynomial and its derivative for given x values. The polynomials are described
; by a vector of their coefficients.
; Next, we'll define functions that use `partial` to define functions for a specific polynomial and its derivative.
; Finally, we'll demonstrate using the functions.
; The `range` function returns a lazy sequence of integers from an inclusive lower bound to an exclusive upper bound.
; The lower bound defaults to 0, the step size defaults to 1, and the upper bound defaults to infinity.
; **********
(defn- polynomial
  "computes the value of a polynomial
  with the given coefficients for a given value x"
  [coefs x]
  ; For example, if coefs contains 3 values then exponents is (2 1 0).
  (let [exponents (reverse (range (count coefs)))]
    ; Multiply each coefficient by x raised to the corresponding exponent
    ; and sum those results
    ; coefs go into %1 and exponents go into %2
    (apply + (map #(* %1 (Math/pow x %2)) coefs exponents))))

(defn- derivative
  "computes the value of the derivative of a polynomial
  with the given coefficients for a given value x"
  [coefs x]
  ; The coefficients of the derivative function are obtained by
  ; multiplying all but the last coefficient by its corresponding exponent.
  ; The extra exponent will be ignored.
  (let [exponents (reverse (range (count coefs)))
        derivative-coefs (map #(* %1 %2) (butlast coefs) exponents)]
    (polynomial derivative-coefs x)))

(def f (partial polynomial [2 1 3])) ; 2x^2 + x + 3
(def f-prime (partial derivative [2 1 3])) ; 4x + 1

(println "f(2) =" (f 2))        ; f(2) = 13.0
(println "f'(2) =" (f-prime 2)) ; f'(2) = 9.0

; Here's an another way that the polynomial function could be implemented (suggested by Francesco Strino).
; For a polynomial with coefficients a, b and c, it computes the value for x as follows:
; %1 = a, %2 = b, result is ax + b
; %1 = ax + b, %2 = c, result is (ax + b)x + c = ax^2 + bx + c
(defn- polynomial
  "computes the value of a polynomial
   with the given coefficients for a given value x"
  [coefs x]
  (reduce #(+ (* x %1) %2) coefs))

