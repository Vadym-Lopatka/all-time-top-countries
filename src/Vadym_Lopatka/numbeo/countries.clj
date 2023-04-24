(ns Vadym-Lopatka.numbeo.countries
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as cs]))

(def numbeo-url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp")

(defn page-content [url]
  (html/html-resource (java.net.URL. url)))

(defn numbeo-period-url [period url]
  (str url "?title=" period))

(defn data-for-period 
  "returns map {period1 to set-of-countries1, period2 to set-of-countries2}"
  [period] 
  (let [page (page-content (numbeo-period-url period numbeo-url))
        countries (map html/text (html/select page [:table#t2 :tbody :tr]))]
    countries))


;; (defn to-countries-coll [raw-countries]
;;   (let [texted-raw-strings (html/text raw-countries)
;;         splitted (cs/split texted-raw-strings #"\n    ")
;;         skipped-first-empty-string (rest splitted)
;;         countries-data (map #(cs/replace % #"\n " "") skipped-first-empty-string)]

;;     countries-data))

;; (defn to-country-to-score-map [countries]
;;   (into (sorted-map)
;;         (map (fn [country-coll] (assoc {}
;;                                        (reduce + (map (fn [numberAsStr] (Double/parseDouble numberAsStr)) (rest country-coll)))
;;                                        (first country-coll))) countries)))



;; (defn get-for-period [period]
;;   (let [data (data-for-period period)
;;         data-as-coll (map to-countries-coll data)
;;         scores (to-country-to-score-map data-as-coll)
;;         top (take-top 10 scores)]

;;     top))

