(ns Vadym-Lopatka.main
  (:gen-class)
  (:require [Vadym-Lopatka.topcountries.periods :as period]
            [Vadym-Lopatka.topcountries.countries :as countries]
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
;;TODO: 2. + Find out how to configure Timre lib to info log level
;;TODO: 3. - Consider incapsulate scrapping(not pure) functions
;;TODO: 4. - Add unit tests
;;TODO: 5. - Write clear and project-descriptive Readme file
;;TODO: 6. - Write article


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (log/set-min-level! :info)
  (log/info "App is starting with args: " args)
  (let [topCountries (find-countries-always-meet-top 10)]
    (log/info "Found top countries are:")
    (doseq [country topCountries] 
      (println country)))
  )
