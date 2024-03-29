# all-time-top-countries

From time to time, Numbeo publishes an index of the highest living standards per country.
I was curious about which countries always meet the top of the index.

As Numbeo has no free API to fetch the data, I decided to satisfy my curiosity using web-scrapping tooling and Clojure.

#### To find the constant top countries we should do the next steps:
- fetch the available periods;
- download the countries' data for each period;
- reduce the raw data to key-value pairs like {218,5 => USA, 220 => UK..};
- sort the result, so highest index countries goes first;
- take the first N countries from the result of the period;
- only then, determine which countries are present in all final result lists;

#### Used tools:
- Clojure
- Enlive web-scrapping library: https://github.com/cgrand/enlive

### How to:
1. [Install Clojure](https://clojure.org/guides/install_clojure)
2. `git clone https://github.com/Vadym-Lopatka/all-time-top-countries.git`
3. `cd all-time-top-countries`
4. Run: `clojure -X:run-x`
5. Run with specific top size 15:
    `clojure -X:run-x :top 15`

### My findings:
1.  #{"Denmark" "Switzerland"} - always in top 10
2. #{"Australia" "Japan" "Denmark" "Switzerland" "Germany"} - always in top 15

