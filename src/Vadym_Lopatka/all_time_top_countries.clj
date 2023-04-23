(ns Vadym-Lopatka.all-time-top-countries
  (:gen-class)
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as cs]))

(def what-i-need-to-do 
  #{
    "+ fetch the existed data periods (consider drop down on the page)"
    "fetch asked top by each period => should return coll of maps like {1 UK}" 
    "map coll of maps to coll of sets" 
    "find the all-time top via sets intersection"
    })


(def numbeo-url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp")

(defn page-content[url] 
  (html/html-resource (java.net.URL. url)))

(defn find-available-data-periods []
  (let [drop-down-content (html/select (page-content numbeo-url) [[:form.changePageForm html/first-of-type] :select :option])
        periods (mapcat #(html/attr-values % :value) drop-down-content)]
    periods))

;; (defn find-available-data-periods [] 
;;   (let [drop-down-content (html/select (page-content numbeo-url) [[:form.changePageForm html/first-of-type] :select :option])
;;         periods (map html/text drop-down-content)]
;;     periods))

(defn numbeo-period-url [period url]
  (str url "?title=" period))

(defn data-for-period [period]
  (let [page (page-content (numbeo-period-url period numbeo-url))
        countries (map html/text (html/select page [:table#t2 :tbody :tr]))]
    countries))

(defn to-countries-coll [raw-countries]
  (let [texted-raw-strings (html/text raw-countries)
        splitted (cs/split texted-raw-strings #"\n    ")
        skipped-first-empty-string (rest splitted)
        countries-data (map #(cs/replace % #"\n " "") skipped-first-empty-string)]

    countries-data))

(defn to-country-to-score-map [countries]
  (into (sorted-map) 
        (map (fn [country-coll] (assoc {}
          (reduce + (map (fn [numberAsStr] (Double/parseDouble numberAsStr)) (rest country-coll)))
          (first country-coll))) countries)))

(defn take-top [amount ordered-countries]
  (take-last amount ordered-countries))

(defn get-for-period [period]
  (let [data (data-for-period period)
        data-as-coll (map to-countries-coll data)
        scores (to-country-to-score-map data-as-coll)
        top (take-top 10 scores)]
    
    top))

(doseq )

;; (to-country-to-score-map (map to-countries-coll n))

(defn find-top-of-all-time []
  (let [periods (find-available-data-periods)
        period-to-countries (map (fn [period] (assoc {} period (take-top 10 (to-country-to-score-map (to-countries-coll (data-for-period period)))))) periods)
        ]
    period-to-countries))

(def periods find-available-data-periods)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (find-top-of-all-time 5)))
