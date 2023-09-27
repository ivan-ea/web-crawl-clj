(ns news-entries
  "Generate news entries with only the relevant information:
  - the title
  - the number of order (rank)
  - the number of comments
  - points"
  (:require
    [babashka.fs :as fs] ; filesystem utilities
    [net.cgrand.enlive-html :as html] ; html templating library for clojure
    [clojure.inspector :as inspector]))
