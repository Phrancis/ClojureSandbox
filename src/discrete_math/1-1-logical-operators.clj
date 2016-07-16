(defn negation [p]
  "The statement ¬A ('not A') is true if and only if A is false, and false only if A is true"
  (not p))

(defn conjunction [A B]
  "The proposition A ∧ B ('A and B') is true if A and B are both true; else it is false."
  (if
    (and (true? A) (true? B))
    true
    false))

(defn inclusive-disjunction [A B]
  "The statement A ∨ B ('A or B') is true if A or B (or both) are true; if both are false, the statement is false."
  (if (or (true? A) (true? B))
    true
    false))

(defn exclusive-disjunction [A B]
  "The statement A ⊕ B ('A or B, but not both') is true when either A or B, but not both, are true."
  (if (or
        (and (true? A) (false? B))
        (and (false? A) (true? B)))
    true
    false))

(defn implication [A B]
  "A → B ('if A, then B' or 'A implies B') is true only in the case that either A is false or B is true."
  (if (or (false? A) (true? B))
    true
    false))

(defn converse [A B]
  "The proposition B → A is the converse of A → B."
  (implication B A))

(defn contrapositive [A B]
  "The proposition ¬B → ¬A is the contrapositive of A → B"
  (implication (negation B) (negation A)))

(defn biconditional [A B]
  "A ↔ B ('A if and only if B') is true only if both A and B are false, or both A and B are true."
  (if
    (or (and (true? A) (true? B))
        (and (false? A) (false? B)))
    true
    false))

(defn -main []
  (println "Negation:")
  (println (negation true))   ; false
  (println (negation false))  ; true
  (println "Conjunction:")
  (println (conjunction true true))   ; true
  (println (conjunction true false))  ; false
  (println (conjunction false true))  ; false
  (println (conjunction false false)) ; false
  (println "Inclusive disjunction:")
  (println (inclusive-disjunction true true))   ; true
  (println (inclusive-disjunction true false))  ; true
  (println (inclusive-disjunction false true))  ; true
  (println (inclusive-disjunction false false)) ; false
  (println "Exclusive disjunction:")
  (println (exclusive-disjunction true true))   ; false
  (println (exclusive-disjunction true false))  ; true
  (println (exclusive-disjunction false true))  ; true
  (println (exclusive-disjunction false false)) ; false
  (println "Implication:")
  (println (implication true true))   ; true
  (println (implication true false))  ; false
  (println (implication false true))  ; true
  (println (implication false false)) ; true
  (println "Converse:")
  (println (converse true true))    ; true
  (println (converse true false))   ; true
  (println (converse false true))   ; false
  (println (converse false false))  ; true
  (println "Contrapositive:")
  (println (contrapositive true true))    ; true
  (println (contrapositive true false))   ; false
  (println (contrapositive false true))   ; true
  (println (contrapositive false false))  ; true
  (println "Biconditional:")
  (println (biconditional true true))   ; true
  (println (biconditional true false))  ; false
  (println (biconditional false true))  ; false
  (println (biconditional false false)) ; true
  )

(-main)
