(ns Vadym-Lopatka.topcountries.webscrapping
  (:require [net.cgrand.enlive-html :as html]))

(defn get-page-as-resource [url]
  (html/html-resource (java.net.URL. url)))
