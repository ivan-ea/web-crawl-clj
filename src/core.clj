(ns core
  (:require
    [clojure.test :refer [run-tests]]))

; todo
(defn filter-1
  "Entries with more than five words in the title ordered by the number of comments."
  []
  (println "filter-1: WIP"))

; todo
(defn filter-2
  "Entries with less than or equal to five words in the title ordered by points."
  []
  (println "filter-2: WIP"))

(defn run-all-tests

  []
  (println "run-all-tests: WIP"))

(defn -main
  "Entry point to interact with the program from the command line"
  [& args]
  (let [[first-arg & rest-args] args]
    (cond
      (= first-arg "1") (filter-1)
      (= first-arg "2") (filter-2)
      (= first-arg "t") (run-all-tests)
      :else (do (println "Usage: clj -M -m core <arg>")
                (println "Values for <arg>: 1, 2, or t")))
    (println "Args" args)))