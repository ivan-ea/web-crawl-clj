(ns filters-test
  (:require
    [filters :refer :all]
    [clojure.test :refer :all]))


(deftest n-words-test
  (is (= 7 (n-words "This sentence should have 7 words :)"))))
