(ns countries.top
  (:require [countries.data :as data]
            [clojure.set :as set]))

(defn find-countries-always-meet-top [size-of-top]
  (let [countries-colls (map first (map vals (data/for-all-periods)))
        tops-colls (map #(take-last size-of-top %) countries-colls)
        country-names-colls (map vals tops-colls)]

    (reduce set/intersection  (map set country-names-colls))))