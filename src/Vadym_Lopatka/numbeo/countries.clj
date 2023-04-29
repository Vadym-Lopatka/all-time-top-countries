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

    countries-data))

(defn parse-number [numberAsStr]
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
        scores (to-country-to-score-map data-as-coll)
        period-to-countries {period scores}]
    
    period-to-countries))


(defn get-countries-for-periods [periods]
  (map get-data-for-period periods))


;;;;;;;;;;;;;;;;;;;;;;;;

;; (defn fetch-available-data-periods []
;;   (let [drop-down-content (html/select (page-content numbeo-url) [[:form.changePageForm html/first-of-type] :select :option])
;;         periods (mapcat #(html/attr-values % :value) drop-down-content)]
;;     periods))

;; (defn get-countries []
;;   (let [periods (fetch-available-data-periods)
;;         period-to-countries (map get-data-for-period periods)]
;;     period-to-countries))

;; (def c (get-countries))

;; (def top (map (fn [[period score-to-country]] (take-last 5 score-to-country)) c))

;; ;; (def v (map vals c))

;; (def t (map (fn [[k vv]] (take-last 5 vv)) c))

;; (doseq [[k v] c] (prn (take-last 5 v)))
;; :subprotocol "mysql"
;; :username "usr"
;; :classname "com.mysql.jdbc.Driver"
;; :subname "//100.100.100.100:3306/clo"
;; :password "pwd"


;; (vals (take-last 5 (first (vals (first cc)))))




