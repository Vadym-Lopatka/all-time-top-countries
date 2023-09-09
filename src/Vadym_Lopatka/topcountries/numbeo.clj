(ns Vadym-Lopatka.topcountries.numbeo
  (:require [net.cgrand.enlive-html :as html]
            [Vadym-Lopatka.topcountries.numbeo :as numbeo]
            [Vadym-Lopatka.topcountries.webscrapping :as webscr]))

(def url "https://www.numbeo.com/quality-of-life/rankings_by_country.jsp")

(def periods-selector [[:form.changePageForm html/first-of-type] :select :option])

(defn fetch-periods []
  (let [page-content (webscr/get-page-as-resource url)
        drop-down-content (html/select page-content periods-selector)]

    (mapcat #(html/attr-values % :value) drop-down-content)))

(defn- to-specific-period-url
  "Builds Numbeo url to specific data period"
  [url period]
  (str url "?title=" period))

(defn fetch-raw-data-for-period
  "Fetch country data for given period"
  [period]
  (let [page (webscr/get-page-as-resource (to-specific-period-url url period))
        countries (map html/text (html/select page [:table#t2 :tbody :tr]))]
    countries))
