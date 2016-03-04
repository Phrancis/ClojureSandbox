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

; There are two ways to access constants in a Java class:
(. java.util.Calendar APRIL) ; 3
(. Calendar APRIL) ; works if the Calendar class was imported
; also as single statement:
java.util.Calendar/APRIL
Calendar/APRIL ; works if the Calendar class was imported

; There are two ways to invoke a static method in a Java class:
(. Math pow 2 4) ; 16.0
(Math/pow 2 4)   ; same

; There are two ways to invoke a constructor to create a Java object. Note the use of the `def` special form to retain
; a reference to the new object in a global binding. This is not required. A reference could be retained in several
; other ways such as adding it to a collection or passing it to a function.
(def calendar (new GregorianCalendar 2016 Calendar/MARCH 3)) ; March 3, 2016
(def calendar (GregorianCalendar. 2016 Calendar/MARCH 3))    ; same


; There are two ways to invoke an instance method on a Java object:
(def calendar (new GregorianCalendar 2016 Calendar/MARCH 3))
(. calendar add Calendar/MONTH 2)
(. calendar get Calendar/MONTH) ; 4
(.add calendar Calendar/MONTH 2)
(.get calendar Calendar/MONTH)  ; 7

; Method calls can be chained using the .. macro.
; The result from the previous method call in the chain becomes the target of the next method call.
(. (. calendar getTimeZone) getDisplayName) ; Eastern Standard Time
(.. calendar getTimeZone getDisplayName)    ; same
; There is also a `.?.` macro in the clojure.core.incubator namespace that stops and returns nil if any method in
; the chain returns null. This avoids getting a NullPointerException.

; The `doto` macro is used to invoke many methods on the same object. It returns the value of its first argument
; which is the target object. This makes it convenient to create the target object with an expression that is
; the first argument (see the creation of a JFrame GUI object in the "Namespaces" section)
(doto calendar
  (.set Calendar/YEAR 2016)
  (.set Calendar/MONTH Calendar/MARCH)
  (.set Calendar/DATE 3))
(def formatter (java.text.DateFormat/getDateInstance))
(println (.format formatter (.getTime calendar))) ; Mar 3, 2016