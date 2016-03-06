(ns
  ^{:author francisveilleux-gaboury}
  clojure1_overview.17a-Vars)

; Vars are references that can have a root binding that is shared by all threads and can have a different value in
; each thread (thread-local).
; To create a Var and give it a root binding:
; => (def name value)

;Providing a value is optional. If none is given then the Var is said to be "unbound". The same form is used
; to change the root binding of an existing Var.
;There are two ways to create a thread-local binding for an existing Var:
; => (binding [name expression] body)
; => (set! name expression) ; inside a binding that bound the same name

;  Use of the `binding` macro was described earlier. The following example demonstrates using it in conjunction with
; the `set!` special form. That changes the thread-local value of a Var that was bound to a thread-local value
; by the `binding` macro.

(def ^:dynamic v 1) ; need "dynamic" metadata so v can be changed in a binding
(defn change-it []
  (println "2) v =" v) ; -> 1
  (def v 2) ; changes root value
  (println "3) v =" v) ; -> 2
  (binding [v 3] ; binds a thread-local value
    (println "4) v =" v) ; -> 3
    (set! v 4) ; changes thread-local value
    (println "5) v =" v)) ; -> 4
  (println "6) v =" v)) ; thread-local value is gone now -> 2

(println "1) v =" v) ; -> 1
(let [thread (Thread. #(change-it))]
  (.start thread)
  (.join thread)) ; wait for thread to finish

(println "7) v =" v) ; -> 2

; The use of Vars is often frowned upon because changes to their values are not coordinated across threads.
; For example, a thread A could use the root value of a Var and then later discover that another thread B changed
; that value before thread A finished executing.