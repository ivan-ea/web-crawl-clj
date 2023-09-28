(ns filters
  "Filtering operations on the news entries"
  (:require
    [clojure.string :as str]
    [babashka.fs :as fs]
    [cheshire.core :as json]))

(def long-short-threshold "Number of words that make a title long or short" 5)

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
  (let [long-title-entries (filter-long-short-titles news-entries :long)]
    ; for ascending order, simply remove the #(compare) fn
    (sort-by :n-comments #(compare %2 %1) long-title-entries)))

(defn filter-2
  "Entries with less than or equal to five words in the title ordered by points."
  [news-entries]
  (let [short-title-entries (filter-long-short-titles news-entries :short)]
    ; for ascending order, simply remove the #(compare) fn
    (sort-by :points #(compare %2 %1) short-title-entries)))

(defn save-filtered-results!
  [news-entries filter-fn output-file]
  (let [filtered (filter-fn news-entries)
        parent-folder (fs/file-name (fs/parent (fs/absolutize output-file)))]
    (spit output-file (json/generate-string filtered {:pretty true}))
    (printf "Written %d entries in %s/%s%n" (count filtered) parent-folder (fs/file-name output-file))))