# web-crawl-clj
Web crawler for Hacker News in Clojure

# Usage
````
clj -M -m core <arg>
````
Values for `<arg>`: 
- `1`: Run filter 1 - Entries with more than five words in the title ordered by the number of comments.
- `2`: Run filter 2 - Entries with less than or equal to five words in the title ordered by points.
- `t`: Run all unit tests