(ns
  clojure1_overview.0-pig-latin)

; returns arg if in set, nil otherwise
(def vowel? (set "aeiou"))

(defn pig-latin [word]
  (let [first-letter (first word)]
    (if (vowel? first-letter)
      (str word "ay")
      (str (subs word 1) first-letter "ay"))))

(println (pig-latin "red"))       ;edray
(println (pig-latin "orange"))    ;orangeay
(println (pig-latin "phrancis"))  ;hrancispay
(println (pig-latin "sirpython")) ;irpythonsay