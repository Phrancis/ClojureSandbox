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
  (if
    (or (true? A) (true? B))
    true
    false))

(defn exclusive-disjunction [A B]
  "The statement A ⊕ B ('A or B, but not both') is true when either A or B, but not both, are true."
  (if (or (true? A) (true? B))
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
  "A ↔ B('A if and only if B') is true only if both A and B are false, or both A and B are true."
  (= A B))

(defn -main []
  (println "Negation:")
  (println (negation true))
  (println (negation false))
  (println "Conjunction:")
  (println (conjunction true true))
  (println (conjunction true false))
  (println (conjunction false true))
  (println (conjunction false false))
  (println "Inclusive disjunction:")
  (println (inclusive-disjunction true true))
  (println (inclusive-disjunction true false))
  (println (inclusive-disjunction false true))
  (println (inclusive-disjunction false false))
  (println "Exclusive disjunction:")
  (println (exclusive-disjunction true true))
  (println (exclusive-disjunction true false))
  (println (exclusive-disjunction false true))
  (println (exclusive-disjunction false false))
  (println "Implication:")
  (println (implication true true))
  (println (implication true false))
  (println (implication false true))
  (println (implication false false))
  (println "Converse:")
  (println (converse true true))
  (println (converse true false))
  (println (converse false true))
  (println (converse false false))
  (println "Contrapositive:")
  (println (contrapositive true true))
  (println (contrapositive true false))
  (println (contrapositive false true))
  (println (contrapositive false false))
  (println "Biconditional:")
  (println (biconditional true true))
  (println (biconditional true false))
  (println (biconditional false true))
  (println (biconditional false false))
  )

(-main)
