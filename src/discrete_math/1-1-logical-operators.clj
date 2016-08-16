; This is meant to be a demonstration of the application of logical operators in code format
; For reference purposes, mathematical notation symbols are used in the doc string of each function.

(defn negation [A]
  "The statement ¬A ('not A') is true if and only if A is false, and false only if A is true"
  (not A))

(defn conjunction [A B]
  "The proposition A ∧ B ('A and B') is true if A and B are both true; else it is false."
  (and (true? A)
       (true? B)))

(defn inclusive-disjunction [A B]
  "The statement A ∨ B ('A or B') is true if A or B (or both) are true; if both are false, the statement is false."
  (or (true? A)
      (true? B)))

(defn exclusive-disjunction [A B]
  "The statement A ⊕ B ('A or B, but not both') is true when either A or B, but not both, are true."
  (or (and (true?  A)
           (false? B))
      (and (false? A)
           (true?  B))))

(defn implication [A B]
  "A → B ('if A, then B' or 'A implies B') is true only in the case that either A is false or B is true."
  (or (false? A)
      (true?  B)))

(defn converse [A B]
  "The proposition B → A is the converse of A → B."
  (implication B A))

(defn contrapositive [A B]
  "The proposition ¬B → ¬A is the contrapositive of A → B"
  (implication (not B)
               (not A)))

(defn biconditional [A B]
  "A ↔ B ('A if and only if B') is true only if both A and B are false, or both A and B are true."
  (or (and (true?  A)
           (true?  B))
      (and (false? A)
           (false? B))))

(defn -main [& args]
  "MAIN: Display the result of each logical operation when applied to each possible combination of true/false values."
  (println
    "Negation:"
    \newline (negation true)    ; false
    \newline (negation false))  ; true
  (println
    "Conjunction:"
    \newline (conjunction true true)    ; true
    \newline (conjunction true false)   ; false
    \newline (conjunction false true)   ; false
    \newline (conjunction false false)) ; false
  (println
    "Inclusive disjunction:"
    \newline (inclusive-disjunction true true)    ; true
    \newline (inclusive-disjunction true false)   ; true
    \newline (inclusive-disjunction false true)   ; true
    \newline (inclusive-disjunction false false)) ; false
  (println
    "Exclusive disjunction:"
    \newline (exclusive-disjunction true true)    ; false
    \newline (exclusive-disjunction true false)   ; true
    \newline (exclusive-disjunction false true)   ; true
    \newline (exclusive-disjunction false false)) ; false
  (println
    "Implication:"
    \newline (implication true true)    ; true
    \newline (implication true false)   ; false
    \newline (implication false true)   ; true
    \newline (implication false false)) ; true
  (println
    "Converse:"
    \newline (converse true true)     ; true
    \newline (converse true false)    ; true
    \newline (converse false true)    ; false
    \newline (converse false false))  ; true
  (println
    "Contrapositive:"
    \newline (contrapositive true true)    ; true
    \newline (contrapositive true false)   ; false
    \newline (contrapositive false true)   ; true
    \newline (contrapositive false false)) ; true
  (println
    "Biconditional:"
    \newline (biconditional true true)    ; true
    \newline (biconditional true false)   ; false
    \newline (biconditional false true)   ; false
    \newline (biconditional false false)) ; true
  )

(-main)
