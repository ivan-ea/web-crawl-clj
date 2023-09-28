(ns news-entries-test
  (:require
    [config :refer [INPUTS]]
    [news-entries :refer :all]
    [clojure.test :refer :all]
    [babashka.fs :as fs]
    [net.cgrand.enlive-html :as html]                       ; html templating library for clojure
    ))

(def parsed-html (html/html-resource (get-in INPUTS ["1" :source])))

(def athings (html/select parsed-html [:tr.athing]))
(def subtexts (html/select parsed-html [:td.subtext]))

(deftest local-html-has-30-entries
  (testing "The local html has news entries that consist of 30 'athings' and 30 'subtexts'"
    (is (= 30 (count athings)))
    (is (= 30 (count subtexts)))))

(deftest content-1st-test
  (is (= "Hello" (content-1st [{:tag :a :content "Hello" :attrs {}} {:other nil}])))
  (is (= nil (content-1st {:a 1 :content 2}))))

(deftest get-title-test
  (is (= "macOS Containers v0.0.1" (get-title (first athings))))
  (is (= "Svix (YC W21) Is Hiring a Technical Lead (US Remote)" (get-title (nth athings 23)))))

(deftest get-rank-test
  (is (= "1." (get-rank (first athings))))
  (is (= "24." (get-rank (nth athings 23)))))

(deftest get-int-test
  (is (= 22 (get-int "22 points")))
  (is (= 123 (get-int "123 comments"))))

(deftest get-points-test
  (is (= 187 (get-points (first subtexts))))
  (is (= 0 (get-points (nth subtexts 23)))))

(deftest get-n-comments-test
  (is (= 119 (get-n-comments (first subtexts))))
  (is (= 0 (get-n-comments (nth subtexts 23)))))

(deftest build-news-entry-test
  (is (= (map->NewsEntry {:title "macOS Containers v0.0.1" :rank "1." :points 187 :n-comments 119})
         (build-news-entry (first athings) (first subtexts))))
  (is (= (map->NewsEntry {:title "Svix (YC W21) Is Hiring a Technical Lead (US Remote)"
                          :rank "24." :points 0 :n-comments 0})
         (build-news-entry (nth athings 23) (nth subtexts 23))))
  )
