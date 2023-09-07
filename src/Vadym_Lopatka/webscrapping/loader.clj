(ns Vadym-Lopatka.webscrapping.loader
  (:require [net.cgrand.enlive-html :as html]))

(def url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp")

(defn get-page-as-resource [url]
  (html/html-resource (java.net.URL. url)))
