(ns core
  (:require
    [filters]
    [clojure.test :refer [run-tests]]
    [net.cgrand.enlive-html :as html] ; html templating library for clojure
    ))



(defn run-all-tests
  "Require and run all unit tests for this project"
  []
  (println "run-all-tests: WIP"))

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