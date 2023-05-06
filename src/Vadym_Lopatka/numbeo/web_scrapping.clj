(ns Vadym-Lopatka.numbeo.web-scrapping
  (:require [net.cgrand.enlive-html :as html]))

(def url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp")

(defn page-content [url]
  (html/html-resource (java.net.URL. url)))
