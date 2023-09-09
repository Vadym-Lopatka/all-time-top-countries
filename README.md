# all-time-top-countries

The Numbeo index is published twice a year and tries to reflect the countries with the highest living standards.

One day next interesting question poped-up in my head: 
what countries are constantly presented at the top of the index?

To find the answer we should do the next steps:
- fetch the available periods that Numbeo contains;
- download the countries' data for each period;
- process the raw data to get a map like {218,5 => USA, 220 => UK, 217 => GERMANY...etc};
- sort the given map in descending order, so that the country with the highest index for the period goes first;
- take the first N countries from the collection to get the top for the period;
- determine which countries are present in all final lists (i.e. always presented at the top);

## to run the project:
    $ clojure -X:run-x
    Countries that are always in top 10 are next: #{"Denmark" "Switzerland"}

## to run the project and pass the size of the top to consider = 15:
    $ clojure -X:run-x :top 15
    Countries that are always in top 15 are next: #{"Australia" "Japan" "Denmark" "Switzerland" "Germany"}

# Used tools:
- Clojure
- Enlive web-scrapping library: https://github.com/cgrand/enlive



