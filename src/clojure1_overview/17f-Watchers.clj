(ns
  clojure1_overview.17f-Watchers)


;
;Agents can act as watchers of other reference type objects. After the value of a watched reference has changed, the
; Agent is notified by sending it an action. The type of the send, send or send-off, is specified when the watcher is
; registered with a reference object. The action function is passed the current value of the Agent (not the value of
; the reference object that changed) and the reference object whose state changed. The return value of the action function
; becomes the new value of the Agent.
;
;As stated earlier, functional programming emphasizes the use of "pure functions" which have no side effects such as
; changing global state. Clojure doesn't prevent functions from doing this, but it does make it easy to find the functions
; that do. One way is to search the source code for functions and macros that call any of the small set of functions
; that change global state (for example, alter). Another way is to use watchers to detect changes. A watcher could print
; a stack trace to identify the function that made the change.
;
;The example below registers a watcher Agent with a Var, a Ref and an Atom. The state of the watcher Agent is a map
; that is used to count the number of times each reference it is watching changes. The keys in this map are the
; reference objects and the values are change counts.
(def my-watcher (agent {}))

(defn my-watcher-action [current-value reference]
  (let [change-count-map current-value
        old-count (change-count-map reference)
        new-count (if old-count (inc old-count) 1)]
    ; Return an updated map of change counts
    ; that will become the new value of the Agent.
    (assoc change-count-map reference new-count)))

(def my-var "v1")
(def my-ref (ref "r1"))
(def my-atom (atom "a1"))

;TODO
;WARNING: This section needs to be updated for changes made in Clojure 1.1. The `add-watcher` and `remove-watcher` functions
; have been removed. The `add-watch` and `remove-watch` functions, that work differently, have been added.
(add-watcher (var my-var) :send-off my-watcher my-watcher-action)
(add-watcher my-ref :send-off my-watcher my-watcher-action)
(add-watcher my-atom :send-off my-watcher my-watcher-action)

; Change the root binding of the Var in two ways.
(def my-var "v2")
(alter-var-root (var my-var) (fn [curr-val] "v3"))

; Change the Ref in two ways.
(dosync
  ; The next line only changes the in-transaction value
  ; so the watcher isn't notified.
  (ref-set my-ref "r2")
  ; When the transaction commits, the watcher is
  ; notified of one change this Ref ... the last one.
  (ref-set my-ref "r3"))
(dosync
  (alter my-ref (fn [_] "r4"))) ; And now one more.

; Change the Atom in two ways.
(reset! my-atom "a2")
(compare-and-set! my-atom @my-atom "a3")

; Wait for all the actions sent to the watcher Agent to complete.
(await my-watcher)

; Output the number of changes to
; each reference object that was watched.
(let [change-count-map @my-watcher]
  (println "my-var changes =" (change-count-map (var my-var))) ; -> 2
  (println "my-ref changes =" (change-count-map my-ref)) ; -> 2
  (println "my-atom changes =" (change-count-map my-atom))) ; -> 2

(shutdown-agents)
