(ns filters-test
  (:require
    [news-entries]
    [news-entries-test :refer [athings subtexts]]
    [filters :refer :all]
    [clojure.test :refer :all]))

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
