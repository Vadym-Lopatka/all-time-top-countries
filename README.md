# all-time-top-countries

The Numbeo which publishes twice a year tries to reflect the countries with the highest living standards. 
It became interesting to find out which countries always meet the top of the index.

As Numbeo has no free API to fetch the data - the only way to satisfy a curiosity is to play around using web-scrapping opportunities.

To find the constant top countries we should do the next steps:
- fetch the available periods that Numbeo contains;
- download the countries' data for each period;
- process the raw data to get a map like {218,5 => USA, 220 => UK, 217 => GERMANY...etc};
- sort the given map in descending order, so that the country with the highest index for the period goes first;
- take the first N countries from the collection to get the top for the period;
- determine which countries are present in all final lists (i.e. always presented at the top);

My discoveries:
- always meets top 10 - #{"Denmark" "Switzerland"};
- always meets top 15 - #{"Australia" "Japan" "Denmark" "Switzerland" "Germany"};

# Used tools:
- Clojure
- Enlive web-scrapping library: https://github.com/cgrand/enlive

# How to run the project
1. Install Clojure: https://clojure.org/guides/install_clojure
2. Clone project
    $ git clone https://github.com/Vadym-Lopatka/all-time-top-countries.git
3. Enter directory:
    $ cd all-time-top-countries
4. Run the project:

### to run with default top(10):
    $ clojure -X:run-x

## to run with custom top param. For example, 15:
    $ clojure -X:run-x :top 15




