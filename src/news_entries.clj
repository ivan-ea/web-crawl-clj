(ns news-entries
  "Generate news entries with only the relevant information:
  - the title
  - the number of order (rank)
  - the number of comments
  - points
  The html elements of concern are:
  - Table rows (tr) of class `athing` have the *title* and the *rank*
  - Table cells (td) of class `subtext` have the *points* and he *number of comments*"
  (:require
    [clojure.string :as str]
    [net.cgrand.enlive-html :as html]                       ; html templating library for clojure
    [clojure.inspector :as inspector]))

(defn content-1st
  "Auxiliary function.
  Gets the content under the :content keyword in the first of a sequence of hash-maps"
  [l]
  (:content (first l)))

(defn get-title
  "Gets the title string from a parsed html 'athing' element"
  [athing-html-parsed]
  (first (content-1st (html/select athing-html-parsed [:td.title :a]))))

(defn get-rank
  "Gets the rank (as a string) from a parsed html 'athing' element"
  [athing-html-parsed]
  (first (content-1st (html/select athing-html-parsed [:td.title :span.rank]))))


; Points and comments are slightly more complicated, since there can be news-entries without them (ads)

(defn get-int
  "Auxiliary function.
  Keep the integer number from a string of the form 'number[space]word'.
  Robust against &nsbp; (non-breaking space)"
  [s]
  (Integer/parseInt (first (str/split s #"\p{Z}"))))

(defn get-points
  "Gets the points (as an int) from a parsed html 'subtext' element"
  [subtext-html-parsed]
  (let [points-str
        (first (content-1st (html/select subtext-html-parsed [:span.subline :span.score])))]
    (if (nil? points-str) 0
                          (get-int points-str))))

(defn get-n-comments
  "Get the number of comments (as an int) from a parsed html 'subtext' element"
  [subtext-html-parsed]
  (let [comments-str (-> subtext-html-parsed
                         :content content-1st butlast last :content first)]
    (if (or (nil? comments-str) (= comments-str "discuss")) 0
                                                            (get-int comments-str))))

(defrecord NewsEntry [title rank points n-comments])

(defn build-news-entry
  "Selects the relevant information from the parsed html of 1 news entry. Generates a news record"
  [athing-html-parsed, subtext-html-parsed]
  (map->NewsEntry {:title      (get-title athing-html-parsed)
                   :rank       (get-rank athing-html-parsed)
                   :points     (get-points subtext-html-parsed)
                   :n-comments (get-n-comments subtext-html-parsed)}))