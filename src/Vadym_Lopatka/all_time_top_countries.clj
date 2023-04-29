(ns Vadym-Lopatka.all-time-top-countries
  (:gen-class)
  (:require [Vadym-Lopatka.numbeo.periods :as period]
            [Vadym-Lopatka.numbeo.countries :as countries]
            [clojure.set :as set]))

(defn get-all-periods-data []
  (let [periods (period/fetch-available-data-periods)] 
    (countries/get-countries-for-periods periods)))


(defn find-all-time-top-countries [size-of-top]
  (let [data (map first (map vals (get-all-periods-data)))
        tops (map #(take-last size-of-top %) data)
        country-names (map vals tops)
        ]
    (reduce set/intersection  (map set country-names))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (find-all-time-top-countries 10)))
