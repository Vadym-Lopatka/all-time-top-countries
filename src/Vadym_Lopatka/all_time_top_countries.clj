(ns Vadym-Lopatka.all-time-top-countries
  (:gen-class)
  (:require [Vadym-Lopatka.numbeo-data.periods :as numbeo-period]
            [Vadym-Lopatka.numbeo-data.data-for-period :as numbeo-data]
            [clojure.set :as set]))

;; (defn take-top [amount countries]
  ;; (take-last amount ordered-countries))

(defn get-top-countries-of-all-periods [size-of-top]
  (let [periods (numbeo-period/fetch-available-data-periods)
        period-to-countries (map numbeo-data/data-for-period periods)
        top-countries (take-last size-of-top period-to-countries)
        country-sets (vals top-countries)]
    
    (apply clojure.set/intersection country-sets)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (get-top-countries-of-all-periods 5)))
