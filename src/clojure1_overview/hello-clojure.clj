(ns
  clojure1_overview.hello-clojure)

; a line comment
(comment
  this is a multiline comment
  see?)

(defn hello [name]
  (println "Hello, " name))

(hello "Clojure")