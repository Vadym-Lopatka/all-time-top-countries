(ns Vadym-Lopatka.all-time-top-countries
  (:gen-class)
  (:require [net.cgrand.enlive-html :as html]))

(def what-i-need-to-do 
  #{
    "fetch the amount of existed data periods (consider drop down on the page)"
    "fetch asked top by each period => should return coll of maps like {1 UK}" 
    "map coll of maps to coll of sets" 
    "find the all-time top via sets intersection"
    })


(def numbeo-url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp")

(def numbeo-page-content (html/html-resource (java.net.URL. numbeo-url)))

(defn find-available-data-periods [] 
  (let [drop-down-content (html/select numbeo-page-content [[:form.changePageForm html/first-of-type] :select :option])
        periods (map html/text drop-down-content)]
    periods))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (find-available-data-periods)))
