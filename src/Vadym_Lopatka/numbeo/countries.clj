(ns Vadym-Lopatka.numbeo.countries
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as cs]
            [Vadym-Lopatka.numbeo.periods :as period]))

(def numbeo-url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp")

(defn- page-content [url]
  (html/html-resource (java.net.URL. url)))

(defn- numbeo-period-url [period url]
  (str url "?title=" period))

(defn- raw-data-for-period 
  [period] 
  (let [page (page-content (numbeo-period-url period numbeo-url))
        countries (map html/text (html/select page [:table#t2 :tbody :tr]))]
    (println (str "PERIOD: " period))
    countries))

;; ADD LOG
;; RUN
;; FIND WHY "-" symbol happens in countries data
;; FIX all-countries-merg-problem

(defn- to-countries-coll [raw-countries]
  (let [texted-raw-strings (html/text raw-countries)
        splitted (cs/split texted-raw-strings #"\n    ")
        skipped-first-empty-string (rest splitted)
        countries-data (map #(cs/replace % #"\n " "") skipped-first-empty-string)]

    (println countries-data)
    countries-data))

(defn parse-number [numberAsStr]
  (try 
    (Double/parseDouble numberAsStr) 
    (catch Exception e
      (println (str "number problem for: " e))
      0)))

(defn- to-country-to-score-map [countries]
  (into (sorted-map)
        (map (fn [country-coll] (assoc {}
                                       (reduce + (map (fn [numberAsStr] (parse-number numberAsStr)) (rest country-coll)))
                                       (first country-coll))) countries)))

(defn get-data-for-period 
  "returns map {period {country-score1 country-name1, country-score2 country-name2}}"
  [period] 
  (let [data (raw-data-for-period period)
        data-as-coll (map to-countries-coll data)
        scores (to-country-to-score-map data-as-coll)
        period-to-countries {period scores}]
    
    period-to-countries))

