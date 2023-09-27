(ns filters
  "Filtering operations on the news entries"
  (:require
    [clojure.string :as str]))

(defn n-words
  "Number of words in a string"
  [s]
  ; split with a regex that matches all whitespace characters
  (count (str/split s #"\p{Z}")))


; todo
(defn filter-1
  "Entries with more than five words in the title ordered by the number of comments."
  [news-entries]
  (println "filter-1: WIP"))

; todo
(defn filter-2
  "Entries with less than or equal to five words in the title ordered by points."
  [news-entries]
  (println "filter-2: WIP"))