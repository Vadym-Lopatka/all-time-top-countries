(ns Vadym-Lopatka.numbeo.countries
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as cs]
            [Vadym-Lopatka.numbeo.source :as source]))

(defn- build-url 
  "Builds Numbeo url to specific data period"
  [url period]
  (str url "?title=" period))

(defn- raw-data-for-period [period] 
  (let [page (source/page-content (build-url source/url period))
        countries (map html/text (html/select page [:table#t2 :tbody :tr]))]
    countries))

(defn- to-countries-coll [raw-countries]
  (let [texted-raw-strings (html/text raw-countries)
        splitted (cs/split texted-raw-strings #"\n    ")
        skipped-first-empty-string (rest splitted)
        countries-data (map #(cs/replace % #"\n " "") skipped-first-empty-string)]

    countries-data))

(defn- parse-number [numberAsStr]
  (try 
    (Double/parseDouble numberAsStr) 
    (catch Exception e
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
        scores (to-country-to-score-map data-as-coll)]
    
    {period scores}))


;; (defn get-countries-for-periods 
;;   "returns list of maps like {period to {contry-score to country-name}}"
;;   [periods]
;;   (map get-data-for-period periods))


