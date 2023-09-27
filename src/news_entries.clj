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

(defn html-entry-2-record
  "Selects the relevant information from the parsed html of 1 news entry. Generates a news record"
  [athing-html subtext-html])