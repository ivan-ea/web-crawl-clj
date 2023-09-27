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
    [babashka.fs :as fs] ; filesystem utilities
    [net.cgrand.enlive-html :as html] ; html templating library for clojure
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

; points and comments are slightly more complicated, since there can be new-entries without them (ads)

(defn html-entry-2-record
  "Selects the relevant information from the parsed html of 1 news entry. Generates a news record"
  [athing-html-parsed subtext-html-parsed])