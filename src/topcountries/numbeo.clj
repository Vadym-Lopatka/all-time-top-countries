(ns Vadym-Lopatka.topcountries.numbeo
  (:require [net.cgrand.enlive-html :as html]
            [Vadym-Lopatka.topcountries.numbeo :as numbeo]
            [Vadym-Lopatka.topcountries.webscrapping :as webscr]))

(def url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp")

(defn fetch-periods []
  (let [page-content (webscr/get-page-as-resource url)
        periods-selector [[:form.changePageForm html/first-of-type] :select :option]
        drop-down-content (html/select page-content periods-selector)]

    (mapcat #(html/attr-values % :value) drop-down-content)))

(defn fetch-raw-country-data-for-period
  "Fetch raw country data for certain period"
  [period]
  (let [url-for-certain-period (str url "?title=" period)
        page-content (webscr/get-page-as-resource url-for-certain-period)
        country-data-selector [:table#t2 :tbody :tr]]
    
    (map html/text (html/select page-content country-data-selector))))
