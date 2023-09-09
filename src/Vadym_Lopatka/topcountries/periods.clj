(ns Vadym-Lopatka.topcountries.periods
  (:require [net.cgrand.enlive-html :as html]
            [Vadym-Lopatka.topcountries.webscrapping :as loader]))

(def periods-selector [[:form.changePageForm html/first-of-type] :select :option])

(defn fetch-periods []
  (let [page-content (loader/get-page-as-resource loader/url)
        drop-down-content (html/select page-content periods-selector)]
    
    (mapcat #(html/attr-values % :value) drop-down-content)))
