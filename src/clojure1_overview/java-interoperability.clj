(ns
  clojure1_overview.java-interoperability)

; Clojure programs can use all Java classes and interfaces. As in Java, classes in the `java.lang` package can be used without
; importing them. Java classes in other packages can be used by either specifying their package when referencing them
; or using the import function. For example:
(import
  '(java.util
     Calendar
     GregorianCalendar)
  '(javax.swing
     JFrame
     JLabel))
; Also see the `:import` directive of the `ns` macro which is described later.