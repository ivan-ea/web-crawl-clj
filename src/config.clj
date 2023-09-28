(ns config
  "Common constants and functions"
  (:require
    [babashka.fs :as fs]))

(def INPUTS
  "Possible inputs for the program: the current website (online) or 2 locally saved versions"

  {"0" {:description   "the current online content of the Hacker News website"
        :source        (java.net.URL. "https://news.ycombinator.com/")
        :out-file-name "hnews_current"}
   "1" {:description   "a saved version of the Hacker News website, from the 26th of September 2023"
        :source        (fs/file "resources" "hnews_23-09-26.html")
        :out-file-name "hnews_23-09-26"}
   "2" {:description   "a saved version of the Hacker News website, from the 27th of September 2023"
        :source        (fs/file "resources" "hnews_23-09-27.html")
        :out-file-name "hnews_23-09-27"}})

(def MAX-ENTRIES 30)

(defn gen-out-file
  "Construct the output file, given the filter name and the input id"
  [filter-name id]
  (fs/file "results" (str (get-in INPUTS [id :out-file-name]) "_" filter-name ".json")))

(defmacro my-time
  "Variation on clojure.core/time: https://github.com/clojure/clojure/blob/clojure-1.10.1/src/clj/clojure/core.clj#L3884
  This macro returns a map with the time taken (duration) and the return value of the expression.
  Useful when timing side effects, when further composition is not usually needed (but still possible)"
  [expr]
  `(let [start# (java.time.Instant/ofEpochMilli (System/currentTimeMillis))
         ret# ~expr ;; evaluates the argument expression
         end# (java.time.Instant/ofEpochMilli (System/currentTimeMillis))
         duration# (java.time.Duration/between start# end#)]
     (hash-map :duration duration# :iso (str duration#)  :return ret#)))