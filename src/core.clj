(ns core
  (:require
    [news-entries]
    [filters]
    [config :refer :all]
    [net.cgrand.enlive-html :as html]                       ; html templating library for clojure
    [clojure.test :refer [run-tests]]
    [babashka.fs :as fs]))

(defn run-all-tests
  "Require and run all unit tests for this project"
  []
  (let [namespaces-to-test ['news-entries-test 'filters-test 'config-test]]
    (apply require namespaces-to-test)
    (println "Running tests for:" namespaces-to-test)
    (apply run-tests namespaces-to-test)))

(defn -main
  "Entry point to interact with the program from the command line"
  [& args]
  (let [[first-arg & rest-args] args
        operation-arg? (contains? INPUTS first-arg)
        input-id (if operation-arg? first-arg "1")
        parsed-html (html/html-resource (get-in INPUTS [input-id :source]))
        athings (html/select parsed-html [:tr.athing])
        subtexts (html/select parsed-html [:td.subtext])
        all-news-entries (take MAX-ENTRIES (map news-entries/build-news-entry athings subtexts))]
    (println "Args are:" args)
    (cond
      operation-arg?
      (do (println "Performing operations on" (get-in INPUTS [input-id :description]))
          (println "Sample entry:" (into {} (nth all-news-entries (int (/ MAX-ENTRIES 3)))))
          (mapv #(filters/save-filtered-results! all-news-entries %1 (gen-out-file %2 input-id))
                [filters/filter-1 filters/filter-2] ["filter_1" "filter_2"]))
      (= first-arg "t") (run-all-tests)
      :else (do (println "Usage: clj -M -m core <arg>")
                (println "Values for <arg>: 0, 1, 2, or t")))))