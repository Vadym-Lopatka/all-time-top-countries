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


(def url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp?title=2017")

(defn get-html-content [url]
  (html/html-resource (java.net.URL. url)))

(defn find-the-tops []
  (println (get-html-content url)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (find-the-tops))
