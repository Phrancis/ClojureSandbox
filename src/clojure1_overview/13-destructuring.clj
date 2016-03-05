(ns
  clojure1_overview.13-destructuring)

; Destructuring can be used in the parameter list of a function or macro to extract parts of collections into local bindings.
; It can also be used in bindings created using the let special form and the binding macro.

; For example, suppose we want a function that takes a list or vector and returns the sum of its first and third items:
(defn approach1
  [numbers]
  (let [n1 (first numbers)
        n3 (nth numbers 2)]
    (+ n1 n3)))
; Note the underscore used to represent the
; second item in the collection which isn't used.
(defn approach2
  [[n1 _ n3]]
  (+ n1 n3))

(approach1 [4 5 6 7]) ; 10
(approach2 [4 5 6 7]) ; 10

; The ampersand character can be used with destructuring to capture the remaining items in a collection.
(defn list-summary
  [[val1 val2 & others]]
  (str val1 ", " val2
    (if (= (count others) 0)
      ""
      (str " and "
        (if (= (count others) 1)
          "1 other"
          (str (count others) " others"))))))

(list-summary ["Scary" "Sporty"])  ; Scary, Sporty
(list-summary ["Scary" "Sporty" "Baby"])  ; Scary, Sporty and 1 other
(list-summary ["Scary" "Sporty" "Baby" "Ginger" "Posh"])  ; Scary, Sporty and 3 others

; The `:as` keyword can be used to retain access to the entire collection that is being destructured.
; Suppose we want a function that takes a list or vector and returns the sum of the first and third items
; divided by the sum of all the items.
(defn first-and-third-percentage
  [[n1 _ n3 :as coll]]
  (/ (+ n1 n3) (apply + coll)))

(first-and-third-percentage [4 5 6 7]) ; 5/11 (ratio reduced from 10/22)

; Destructuring can also be used to extract values from maps.
; Suppose we want a function that takes a map of sales figures where each key is a month and each value is
; the sales total for that month. The function sums the sales for summer months and divides by the sales for
; all the months to determine the percentage of all sales that occurred in the summer.
(defn summer-sales-percentage
  ; This can also be listed by key: {june :june july :july august :august :as all}
  ; That method would have to be used if the binding names didn't match key names
  [{:keys [june july august] :as all}]
  (let [summer-sales (+ june july august)
        all-sales (apply + (vals all))]
    (/ summer-sales all-sales)))

(def sales {
  :january   100 :february 200 :march      0 :april    300
  :may       200 :june     100 :july     400 :august   500
  :september 200 :october  300 :november 400 :december 600})

(println (summer-sales-percentage sales)) ; 10/33 (ratio reduced from 1000/3300)

; It is common when destructuring maps to use local binding names whose names match corresponding keys.
; For example, in the code above we originally used {june :june july :july august :august :as all}.
; This can be simplified using :keys. For example, {:keys [june july august] :as all}.