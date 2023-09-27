(ns core
  (:require
    [news-entries]
    [filters]
    [net.cgrand.enlive-html :as html]                       ; html templating library for clojure
    [clojure.test :refer [run-tests]]))

(def BASE-URL "https://news.ycombinator.com/")
(def MAX-ENTRIES 30)

(defn run-all-tests
  "Require and run all unit tests for this project"
  []
  (let [namespaces-to-test ['news-entries-test 'filters-test]]
    (apply require namespaces-to-test)
    (println "Running tests for:" namespaces-to-test)
    (apply run-tests namespaces-to-test)))

(defn -main
  "Entry point to interact with the program from the command line"
  [& args]
  (let [[first-arg & rest-args] args
        parsed-html (html/html-resource (java.net.URL. BASE-URL))
        athings (html/select parsed-html [:tr.athing])
        subtexts (html/select parsed-html [:td.subtext])
        all-news-entries (take MAX-ENTRIES (map news-entries/build-news-entry athings subtexts))]
    (println "Args are:" args)
    (println "Sample entry:" (into {} (nth all-news-entries 10)))
    (cond
      (= first-arg "1") (filters/filter-1 all-news-entries)
      (= first-arg "2") (filters/filter-2 all-news-entries)
      (= first-arg "12") (do (filters/filter-1 all-news-entries) (filters/filter-2 all-news-entries))
      (= first-arg "t") (run-all-tests)
      :else (do (println "Usage: clj -M -m core <arg>")
                (println "Values for <arg>: 1, 2, 12, or t")))))