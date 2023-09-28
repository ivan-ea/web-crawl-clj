(ns config-test
  (:require
    [config :refer :all]
    [filters]
    [clojure.test :refer :all]
    [babashka.fs :as fs]))

(deftest gen-out-file-test
  (is (= (fs/file "results" "hnews_current_filter_1.json") (gen-out-file "filter_1" "0"))))
