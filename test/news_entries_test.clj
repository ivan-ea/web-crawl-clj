(ns news-entries-test
  (:require
    [news-entries :refer :all]
    [clojure.test :refer :all]
    [babashka.fs :as fs]
    [net.cgrand.enlive-html :as html]                       ; html templating library for clojure
    ))

(def hnews-local-filename "hnews_23-09-26.html")
(def parsed-html (html/html-resource (fs/file "test" hnews-local-filename)))

(def athings (html/select parsed-html [:tr.athing]))
(def subtexts (html/select parsed-html [:td.subtext]))

(deftest local-html-has-30-entries
  (testing "The local html has news entries that consist of 30 'athings' and 30 'subtexts'"
    (is (= 30 (count athings)))
    (is (= 30 (count subtexts)))))