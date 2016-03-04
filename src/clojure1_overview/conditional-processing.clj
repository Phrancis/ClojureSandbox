(ns
  clojure1_overview.conditional-processing)

(import '(java.util Calendar GregorianCalendar))

; The `if` special form tests a condition and executes one of two expressions based on whether the condition evaluates to true.
; Its syntax is (if condition then-expr else-expr). The else expression is optional. If more than one expression is needed
; for the then or else part, use the `do` special form to wrap them in a single expression.
(let [gc (GregorianCalendar.)
      day-of-week (.get gc Calendar/DAY_OF_WEEK)
      is-weekend (or
                   (= day-of-week Calendar/SATURDAY)
                   (= day-of-week Calendar/SUNDAY))
      ]
  (if is-weekend
        (println "Play!")
        ; else...
        (do
          (println "Work.")
          (println "Sleep."))))

; The `when` and `when-not` macros provide alternatives to if when only one branch is needed.
; Any number of body expressions can be supplied without wrapping them in a do.
; (when is-weekend (println "play"))
; (when-not is-weekend (println "work") (println "sleep"))