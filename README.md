# web-crawl-clj
Web crawler for Hacker News in Clojure

The news entries are filtered in two ways:
- Entries with more than five words in the title ordered by the number of comments.
- Entries with less than or equal to five words in the title ordered by points.

# Contents
- [Usage](#usage)
- [Results](#results)
  - [Results for filter 1](#results-for-filter-1)
  - [Results for filter 2](#results-for-filer-2)
  - [Output of unit tests](#output-of-unit-tests)
- [References](#references)

# Usage
Different scenarios can be tested, by changing the argument to the main function.
From the command line:
````
clj -M -m core <arg>
````
Values for `<arg>`: 
- `0`: Run the operations on the current Hacker News website (online).
- `1`: Run the operations on a saved version of the website, from the 26th of September 2023 .
- `2`: Run the operations on a saved version of the website, from the 27th of September 2023.
- `t`: Run all unit tests.

# Results
A typical execution of the program will produce similar output to the one below
````
> clj -M -m core 0
Args are: (0)
Performing operations on the current online content of the Hacker News website
  Time taken parsing html into news-entries: PT2.268S
  Sample entry: {:title Prophetic Perfect Tense, :rank 11., :points 82, :n-comments 39}
Filtering the news entries
  Time taken filtering: PT0.043S
  Written 21 entries in results/hnews_current_filter_1.json
Filtering the news entries
  Time taken filtering: PT0.001S
  Written  9 entries in results/hnews_current_filter_2.json
````

The results are written in json format in the [`results`](./results) folder. 

A [github action](https://github.com/ivan-ea/web-crawl-clj/actions/workflows/hacker_news_unit_tests.yaml) is implemented 
to run on demand, and to provide **the results for the current version of the Hacker News website**.

Below are the results for the operations on [a saved version of the Hacker News website, from the 26th of September 2023](resources/hnews_23-09-26.html).

## Results for filter 1
````
> head results/hnews_23-09-26_filter_1.json
[ {
  "title" : "The SR-71 Blackbird Astro-Nav System worked by tracking the stars",
  "rank" : "7.",                                                                
  "points" : 276,                                                               
  "n-comments" : 257                                                            
}, {                                                                            
  "title" : "Pixel 8 leak promises 7 years of OS updates",                      
  "rank" : "20.",                                                               
  "points" : 147,                                                               
  "n-comments" : 210 
````
````
> tail results/hnews_23-09-26_filter_1.json
  "title" : "Lego axes plan to make bricks from recycled bottles",
  "rank" : "9.",
  "points" : 9,
  "n-comments" : 3
}, {
  "title" : "Svix (YC W21) Is Hiring a Technical Lead (US Remote)",
  "rank" : "24.",
  "points" : 0,
  "n-comments" : 0
} ]

````
## Results for filer 2
````
> head results/hnews_23-09-26_filter_2.json
[ {
  "title" : "Unity's oldest community announces dissolution",
  "rank" : "29.",
  "points" : 586,
  "n-comments" : 365
}, {
  "title" : "DJI Mini 4 Pro",
  "rank" : "3.",
  "points" : 318,
  "n-comments" : 347
````
````
> tail results/hnews_23-09-26_filter_2.json
  "title" : "Is Math Real?",
  "rank" : "8.",
  "points" : 140,
  "n-comments" : 222
}, {
  "title" : "Nickel Hydrogen Batteries by NASA",
  "rank" : "6.",
  "points" : 52,
  "n-comments" : 25
} ]

````

## Output of unit tests
````
> clj -M -m core t
Args are: (t)
Running tests for: [news-entries-test filters-test config-test]

Testing news-entries-test

Testing filters-test
Filtering the news entries
  Time taken filtering: PT0.001S                            
  Written 20 entries in results/hnews_23-09-26_filter_1.json
Filtering the news entries
  Time taken filtering: PT0S
  Written 10 entries in results/hnews_23-09-26_filter_2.json

Testing config-test

Ran 15 tests containing 34 assertions.
0 failures, 0 errors.
Time taken for all unit tests:  PT1.25S
````

# References
- [Forum discussion about querying html in Clojure](https://clojureverse.org/t/best-library-for-querying-html/1103)
- [Enlive tutorial](https://github.com/swannodette/enlive-tutorial/)