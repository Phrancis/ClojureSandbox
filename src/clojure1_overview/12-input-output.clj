(ns
  clojure1_overview.input-output
  (:use [clojure.repl :only (source)]))

; Clojure provides a minimal set of functions that perform I/O operations.
; Since Java classes are easy to use from Clojure code, the classes in the java.io package can be used directly.
; However, the clojure.java.io library makes many uses of those classes easier.

; The predefined, special symbols *in*, *out* and *err* are set to 'stdin', 'stdout' and 'stderr' by default.
; To flush buffered output in *out*, use `(flush)` which is the same as (.flush *out*).
; The bindings for these symbols can be modified. For example, to redirect output that goes to stdout by default
; so it goes to a file named "my.log", surround the code to be affected as follows:
;(binding [*out* (java.io.FileWriter. "my.log")]
;  ...
;  (println "This goes to the file my.log.")
;  ...
;  (flush))

; The `print` function prints the string representation of any number of objects, with a space between each one,
; to the stream in the special symbol *out*.

; The `println` function is like `print`, but it outputs a newline character at the end.
; By default, it also flushes its output. This can be changed by binding the special symbol *flush-on-newline* to false.

; The `newline` function writes a newline character to the stream in *out* .
; Calling `print` followed by `newline` is equivalent to `println`.

; The `pr` and `prn` functions are like their `print` and `println` counterparts, but their output is in a form
; that can be read by the Clojure reader. They are suitable for serializing Clojure data structures.
; By default, they do not print metadata. This can be changed by binding the special symbol *print-meta* to true.

; The following examples demonstrate all four of the printing functions.
; Note how the output for strings and characters differs depending on whether the `print` or `pr` functions are used.
(def obj1 "foo")
(def obj2 {:letter \a :number (Math/PI)}) ; a map

(println "Output from print:")
(print obj1 obj2)

(println "Output from println:")
(println obj1 obj2)

(println "Output from pr:")
(pr obj1 obj2)

(println "Output from prn:")
(prn obj1 obj2)

; CONSOLE OUTPUT:

;Output from print:
;foo {:letter a, :number 3.141592653589793}Output from println:
;foo {:letter a, :number 3.141592653589793}
;Output from pr:
;"foo" {:letter \a, :number 3.141592653589793}Output from prn:
;"foo" {:letter \a, :number 3.141592653589793}

; All the printing functions discussed above add a space between the output of their arguments.
; The `str` function can be used to avoid this. It concatenates the string representations of its arguments.
(println "foo" 42)      ; foo 42
(println (str "foo" 42)); foo42

; The print-str, println-str, pr-str and prn-str functions are similar to their print, println, pr and prn counterparts,
; but they print to a string that is returned instead of printing to *out*.

; The `printf` function is similar to print, but uses a format string.
; The `format` function is similar to `printf`, but prints to a string that is returned instead of printing to *out*.

; The `with-out-str` macro captures the output from all the expressions in its body in a string and returns it.

; The `with-open` macro takes any number of bindings to objects on which `.close` should be called after
; the expressions in its body are executed. It is ideal for processing resources such as files and database connections.

; The `line-seq` function takes a java.io.BufferedReader and returns a lazy sequence of all the lines of text in it.
; The significance of returning a "lazy" sequence is that it doesn't really read all of the lines when it is called.
; That could consume too much memory. Instead it reads a line each time one is requested from the lazy sequence.

; The following example demonstrates both `with-open` and `line-seq`.
; It reads all the lines in a file and prints those that contain a given word.
(use '[clojure.java.io :only (reader)])
(defn print-if-contains [line word]
  (when (.contains line word) (println line)))

(let [file "http://textfiles.com/stories/3lpigs.txt"
      word "little pigs"]
  ; with-open will close the reader after evaluating all the expressions in its body.
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)] (print-if-contains line word))))

; The `slurp` function reads the entire contents of a file into a string and returns it.
; The `spit` function writes a string to a given file and closes it.

; This article only scratches the surface of what the core and java.io libraries provide.
; It's a worthwhile investment to read through the file clojure/java/io.clj to learn about the other functions it defines.