(ns Vadym-Lopatka.numbeo.periods
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as cs]))


(def numbeo-url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp")

(defn page-content [url]
  (html/html-resource (java.net.URL. url)))

(defn fetch-available-data-periods []
  (let [drop-down-content (html/select (page-content numbeo-url) [[:form.changePageForm html/first-of-type] :select :option])
        periods (mapcat #(html/attr-values % :value) drop-down-content)]
    periods))
