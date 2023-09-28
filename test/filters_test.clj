(ns filters-test
  (:require
    [news-entries]
    [news-entries-test :refer [athings subtexts]]
    [filters :refer :all]
    [clojure.test :refer :all]
    [babashka.fs :as fs]
    [cheshire.core :as json]))

(def all-news-entries (map news-entries/build-news-entry athings subtexts))

(deftest num-words-test
  (is (= 7 (num-words "This sentence should have 7 words :)"))))

(deftest filter-long-short-titles-test
  (testing "long titles"
    (is (= 20 (count (filter-long-short-titles all-news-entries :long))))
    (is (= "Lego axes plan to make bricks from recycled bottles"
           (:title (nth (filter-long-short-titles all-news-entries :long) 3)))))
  (testing "short titles"
    (is (= 10 (count (filter-long-short-titles all-news-entries :short))))
    (is (= "Nickel Hydrogen Batteries by NASA" (:title (nth (filter-long-short-titles all-news-entries :short) 3))))))

(deftest filter-1-test
  (let [filtered (filter-1 all-news-entries)]
    (is (= 20 (count filtered)))
    (is (= 171 (:n-comments (nth filtered 2))))
    (is (= 24 (:n-comments (nth filtered 16))))))

(deftest filter-2-test
  (let [filtered (filter-2 all-news-entries)]
    (is (= 10 (count filtered)))
    (is (= 263 (:points (nth filtered 2))))
    (is (= 140 (:points (nth filtered 8))))))

(deftest save-filtered-results!-test
  (testing "Check that writting the results to file and parsing them back are the same as the originals"
    (let [file-1 (fs/file "results" "hnews_23-09-26_filter_1.json")
          file-2 (fs/file "results" "hnews_23-09-26_filter_2.json")
          _ (mapv #(save-filtered-results! all-news-entries %1 %2) [filter-1 filter-2] [file-1 file-2])
          filtered-1 (map #(into {} %) (filter-1 all-news-entries))
          filtered-2 (map #(into {} %) (filter-2 all-news-entries))
          saved-filter-1 (json/parse-string (slurp file-1) true)
          saved-filter-2 (json/parse-string (slurp file-2) true)]
      (is (= filtered-1 saved-filter-1))
      (is (= filtered-2 saved-filter-2)))))
