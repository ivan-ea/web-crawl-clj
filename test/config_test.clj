(ns config-test
  (:require
    [config :refer :all]
    [filters]
    [clojure.test :refer :all]
    [babashka.fs :as fs]))

(deftest gen-out-file-test
  (is (= (fs/file "results" "hnews_current_filter_1.json") (gen-out-file "filter_1" "0"))))

(deftest my-time-test
  (let [time-map (my-time (do (Thread/sleep 1050)
                              (apply + (take 5 (range 1 100 2)))))
        iso-str (:iso time-map)]
    (is (= (* 5 5) (:return time-map)))
    (is (>= 1 (.getSeconds (:duration time-map))))
    (is (<= (.getNano (:duration time-map)) 1e9) "time taken is 1s and a little bit more (not 2s)")
    (is (= [iso-str "1" "S"] (re-matches #"PT(\d).+\d+(S)" iso-str)))))
