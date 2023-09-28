(ns core
  (:require
    [news-entries]
    [filters]
    [config :refer :all]
    [net.cgrand.enlive-html :as html]                       ; html templating library for clojure
    [clojure.test :refer [run-tests]]))

(defn run-all-tests
  "Require and run all unit tests for this project"
  []
  (let [namespaces-to-test ['news-entries-test 'filters-test 'config-test]]
    (apply require namespaces-to-test)
    (println "Running tests for:" namespaces-to-test)
    (let [timed (my-time (apply run-tests namespaces-to-test))]
      (println "Time taken for all unit tests: " (:iso timed)))))

(defn parse-html
  "Parse the html corresponding to the desired input resource into news-entries"
  [input-id]
  (let [parsed-html (html/html-resource (get-in INPUTS [input-id :source]))
        athings (html/select parsed-html [:tr.athing])
        subtexts (html/select parsed-html [:td.subtext])]
    (take MAX-ENTRIES (map news-entries/build-news-entry athings subtexts))))

(defn -main
  "Entry point to interact with the program from the command line"
  [& args]
  (let [[first-arg & rest-args] args
        operation-arg? (contains? INPUTS first-arg)
        input-id (if operation-arg? first-arg "1")]
    (println "Args are:" args)
    (cond
      operation-arg?
      (let [{all-news-entries :return parse-time :iso} (my-time (parse-html input-id))]
        (println "Performing operations on" (get-in INPUTS [input-id :description]))
        (println "  Time taken parsing html into news-entries:" parse-time)
        (println "  Sample entry:" (into {} (nth all-news-entries (int (/ MAX-ENTRIES 3)))))
        (mapv #(filters/save-filtered-results! all-news-entries %1 (gen-out-file %2 input-id))
              [filters/filter-1 filters/filter-2] ["filter_1" "filter_2"]))
      (= first-arg "t") (run-all-tests)
      :else (do (println "Usage: clj -M -m core <arg>")
                (println "Values for <arg>: 0, 1, 2, or t")))))