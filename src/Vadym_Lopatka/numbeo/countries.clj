(ns Vadym-Lopatka.numbeo.countries
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as cs]
            [Vadym-Lopatka.webscrapping.loader :as loader]
            [taoensso.timbre :as log]))

(defn- build-url 
  "Builds Numbeo url to specific data period"
  [url period]
  (str url "?title=" period))

(defn- raw-data-for-period 
  "Fetch country data for given period"
  [period] 
  (let [page (loader/get-page-as-resource (build-url loader/url period))
        countries (map html/text (html/select page [:table#t2 :tbody :tr]))]
    countries))

(defn- build-country-data-coll 
  "Convert raw scrapped country data into country data collection"
  [raw-countries]
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

(defn- country-score [country-coll]
  (reduce + (map (fn [numberAsStr] (parse-number numberAsStr)) (rest country-coll))))

(defn- country-name [country-coll]
  (first country-coll))

(defn- to-country-to-score-map 
  "Converts contry data coll (UK 0.11 200 33) into Map entry, {like 233.11 => UK}"
  [countries]
  (into (sorted-map)
        (map (fn [country-coll] (assoc {} (country-score country-coll) (country-name country-coll))) countries)))

(defn get-data-for-period 
  "returns PersistentArrayMap: {period {country-score1 country-name1...}}"
  [period]
  (log/debug "Request to get data for period: " period)
  (let [data (raw-data-for-period period)
        countries-data-colls (map build-country-data-coll data)
        score-to-country-map (to-country-to-score-map countries-data-colls)]
    
    {period score-to-country-map}))
