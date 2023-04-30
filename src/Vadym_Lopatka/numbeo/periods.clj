(ns Vadym-Lopatka.numbeo.periods
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as cs]))

(def url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp")

(def dropdown-periods-selector [[:form.changePageForm html/first-of-type] :select :option])

(defn page-content [url]
  (html/html-resource (java.net.URL. url)))

(defn fetch-available-data-periods []
  (let [drop-down-content (html/select (page-content url) dropdown-periods-selector)
        periods (mapcat #(html/attr-values % :value) drop-down-content)]
    periods))
