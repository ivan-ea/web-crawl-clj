(ns config
  "Constants for the program"
  (:require
    [babashka.fs :as fs]))

(def INPUTS
  "Possible inputs for the program: the current website (online) or 2 locally saved versions"

  {"0" {:description   "the base Hacker News url, to work with the current online content"
        :source        (java.net.URL. "https://news.ycombinator.com/")
        :out-file-name "hnews_current"}
   "1" {:description   "a saved version of the Hacker News website, from the 26th of September 2023"
        :source        (fs/file "resources" "hnews_23-09-26.html")
        :out-file-name "hnews_23-09-26"}
   "2" {:description   "a saved version of the Hacker News website, from the 27th of September 2023"
        :source        (fs/file "resources" "hnews_23-09-27.html")
        :out-file-name "hnews_23-09-27"}})

(def MAX-ENTRIES 30)