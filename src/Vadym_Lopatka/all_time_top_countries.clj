(ns Vadym-Lopatka.all-time-top-countries
  (:gen-class)
  (:require [Vadym-Lopatka.numbeo.periods :as period]
            [Vadym-Lopatka.numbeo.countries :as countries]
            [clojure.set :as set]))

(defn get-top-countries-of-all-periods [size-of-top]
  (let [periods (period/fetch-available-data-periods)
        period-to-countries (map countries/data-for-period periods)
        top-countries (take-last size-of-top period-to-countries)
        country-sets (vals top-countries)]
    
    (apply clojure.set/intersection country-sets)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (get-top-countries-of-all-periods 5)))
