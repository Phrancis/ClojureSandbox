(ns
  clojure1_overview.1-vars)

(def ^:dynamic v 1) ; v is a global binding

(defn f1 []
  (println "f1: v:" v))

(defn f2 []
  (println "f2: before let v:" v)
  ; creates local binding v that shadows global one
  (let [v 2]
    ; local binding only within this let statement
    (println "f2: in let, v:" v)
    (f1)) ; calling f1 with original value to v
  ; outside of this let, v refers to global binding
  (println "f2: after let v:" v))

(defn f3 []
  (println "f3: before binding v:" v)
  ; same global binding with new, temporary value
  (binding [v 3]
    ; global binding, new value
    (println "f3: within binding function v: " v)
    (f1)) ; calling f1 with new value to v
  ; outside of binding v refers to first global value
  (println "f3: after binding v:" v))

(defn f4 []
  (def v 4)) ; changes the value of v in the global scope

(println "(= v 1) => " (= v 1))
(println "Calling f1: ")
(f1)
(println)
(println "Calling f2: ")
(f2)
(println)
(println "Calling f3: ")
(f3)
(println)
(println "Calling f4: ")
(f4)
(println "after calling f4, v =" v)

(comment prints this...
;  (= v 1) =>  true
;  Calling f1:
;  f1: v: 1
;
;  Calling f2:
;  f2: before let v: 1
;  f2: in let, v: 2
;  f1: v: 1
;  f2: after let v: 1
;
;  Calling f3:
;  f3: before binding v: 1
;  f3: within binding function v:  3
;  f1: v: 3
;  f3: after binding v: 1
;
;  Calling f4:
;  after calling f4, v = 4
  )