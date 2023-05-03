(ns Vadym-Lopatka.all-time-top-countries
  (:gen-class)
  (:require [Vadym-Lopatka.numbeo.periods :as period]
            [Vadym-Lopatka.numbeo.countries :as countries]
            [clojure.set :as set]
            [taoensso.timbre :as log]))

(defn get-data-for-all-periods []
  (let [periods (period/fetch-periods)] 
    (log/info "Found time periods: " periods)
    (map countries/get-data-for-period periods)))

(defn find-countries-always-meet-top [size-of-top]
  (log/info "Counting countries only among the TOP - " size-of-top)
  (let [countries-colls (map first (map vals (get-data-for-all-periods)))
        tops-colls (map #(take-last size-of-top %) countries-colls)
        country-names-colls (map vals tops-colls)]
    
    (reduce set/intersection  (map set country-names-colls))))

;;TODO: 1. + Find logger
;;TODO: 2. + Add logs
;;TODO: 3. - Consider incapsulate scrapping(not pure) functions
;;TODO: 4. - Add unit tests
;;TODO: 5. - Write clear and project-descriptive Readme file
;;TODO: 6. - Write article


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (log/info "Found top countries are: " (find-countries-always-meet-top 10)))
