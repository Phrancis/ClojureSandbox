(ns
  clojure1_overview.conditional-processing)

(import '(java.util Calendar GregorianCalendar))

; The `if` special form tests a condition and executes one of two expressions based on whether the condition evaluates to true.
; Its syntax is (if condition then-expr else-expr). The else expression is optional. If more than one expression is needed
; for the then or else part, use the `do` special form to wrap them in a single expression.

;(let [gc (GregorianCalendar.)
;      day-of-week (.get gc Calendar/DAY_OF_WEEK)
;      is-weekend (or
;                   (= day-of-week Calendar/SATURDAY)
;                   (= day-of-week Calendar/SUNDAY))
;      ]
;  (if is-weekend
;        (println "Play!")
;        ; else...
;        (do
;          (println "Work.")
;          (println "Sleep."))))

; The `when` and `when-not` macros provide alternatives to if when only one branch is needed.
; Any number of body expressions can be supplied without wrapping them in a do.
; (when is-weekend (println "play"))
; (when-not is-weekend (println "work") (println "sleep"))

; The `if-let` macro binds a value to a single binding and chooses an expression to evaluate based on whether the value
; is logically true or false.
; The following code prints the name of the first person waiting in line or prints "no waiting" if the line is empty.
(defn process-next [waiting-line]
  (if-let [name (first waiting-line)]
    (println name "is next")
    (println "no waiting")))
; (process-next '("Simon" "Phrancis" "SirPython")) ; -> Simon is next
; (process-next '()) ; -> no waiting

; The `when-let` macro is similar to the `if-let` macro, but it differs in the same way that `if` differs from `when`.
; It doesn't support an else part and the then part can contain any number of expressions.
(defn summarize
  "prints the first item in a collection followed by
  a priod for each remaining item"
  [coll]
  ; Execute the when-let body only if collection isn't empty
  (when-let [head (first coll)]
    (print head)
    ; Below, dec subtracts one (decrements) from the number of items in collection
    (dotimes [_ (dec (count coll))] (print \.))
    (println)))
; (summarize ["foo" "bar" "fizz" "buzz"]) ; foo...
; (summarize []) ; no output

; The `condp` macro is similar to a case statement in other languages. It takes a two parameter predicate
; (often = or `instance?`) and an expression to act as its second argument. After those it takes any number of
; value/result expression pairs that are evaluated in order. If the predicate evaluates to true when one of the values
; is used as its first argument, then the corresponding result is returned.
; An optional final argument specifies the result to be returned if no given value causes the predicate to evaluate to true.
; If this is omitted and no given value causes the predicate to evaluate to true then an IllegalArgumentException is thrown.
(defn give-me-a-number
  "1. Prompts user for a number using stdin
   2. Prints the name of the number (only for 1 2 3) or `unexpected value`
   3. a) If it is a Number, it prints the number times two.
      b) If it is a String, it prints the length of the string times two."
  [] ; no args
  (print "Enter a number: ") (flush) ; stays in buffer otherwise
  (let [reader (java.io.BufferedReader. *in*) ; stdin
        line (.readLine reader)
        value (try
                (Integer/parseInt line)
                (catch NumberFormatException e line))] ; return string value if not integer
    (println
      (condp = value
        1 "one"
        2 "two"
        3 "three"
        (str "unexpected value, \"" value \")))
    (println
      (condp instance? value
        Number (* value 2)
        String (* (count value) 2)))))
; (give-me-a-number)
;Enter a number: 2
;two
;4
;Enter a number: hello
;unexpected value, "hello"
;10

; The cond macro takes any number of predicate/result expression pairs. It evaluates the predicates in order
; until one evaluates to true and then returns the corresponding result.
; If none evaluate to true then an IllegalArgumentException is thrown.
; Often the predicate in the last pair is simply `true` to handle all remaining cases.
(defn water-temp
  "1) Prompts the user to enter a water temperature.
   2) prints whether the water is freezing, boiling or neither"
  [] ; no args
  (print "Enter water temperature in Celsius: ") (flush)
  (let [reader (java.io.BufferedReader. *in*)
        line (.readLine reader)
        temp (try
               (Float/parseFloat line)
               (catch NumberFormatException e line))]
    (println
      (cond
        (instance? String temp) (str "Invalid temperature value: " temp)
        (<= temp 0) "freezing"
        (>= temp 100) "boiling"
        true "neither freezing nor boiling"))))
(water-temp)
;Enter water temperature in Celsius: 13
;neither freezing nor boiling
;Enter water temperature in Celsius: foo
;Invalid temperature value: foo