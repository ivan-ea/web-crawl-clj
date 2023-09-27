(ns filters
  "Filtering operations on the news entries"
  (:require
    [clojure.string :as str]))

(def long-short-threshold "Number of words that make a title short or long" 5)

(defn num-words
  "Returns the number of words in a string"
  [s]
  ; split with a regex that matches all whitespace characters
  (count (str/split s #"\p{Z}")))

(defn filter-long-short-titles
  "Filters news entries with the key_ :long or :short, uses the defined threshold"
  [news-entries key_]
  (let [comp-fn (if (= key_ :long) > <=)]
    (filter #(comp-fn (num-words (:title %)) long-short-threshold) news-entries)))

(defn filter-1
  "Entries with more than five words in the title ordered by the number of comments."
  [news-entries]
  (let [with-long-titles (filter-long-short-titles news-entries :long)]
    ; remove the compare fn for ascending order
    (sort-by :n-comments #(compare %2 %1) with-long-titles)))



; todo
(defn filter-2
  "Entries with less than or equal to five words in the title ordered by points."
  [news-entries]
  (println "filter-2: WIP"))

;todo print results on a json
(defn save-filtered-results
  [filtered-news-entries filter-name])