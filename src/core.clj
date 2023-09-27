(ns core
  (:require
    [filters]
    [clojure.test :refer [run-tests]]
    [net.cgrand.enlive-html :as html]                       ; html templating library for clojure
    ))

(def base-url "https://news.ycombinator.com/")



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
        parsed-html nil]
    (cond
      (= first-arg "1") (filters/filter-1 parsed-html)
      (= first-arg "2") (filters/filter-2 parsed-html)
      (= first-arg "t") (run-all-tests)
      :else (do (println "Usage: clj -M -m core <arg>")
                (println "Values for <arg>: 1, 2, or t")))
    (println "Args" args)))