# web-crawl-clj
Web crawler for Hacker News in Clojure

The news entries are filtered in 2 ways:
- Entries with more than five words in the title ordered by the number of comments.
- Entries with less than or equal to five words in the title ordered by points.


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

# Resources