(ns Vadym-Lopatka.main
  (:gen-class)
  (:require [Vadym-Lopatka.topcountries.top :as top]
            [taoensso.timbre :as log]))

(defn -main [& args]
  (log/info "App is starting..." )
  (let [default-top 10
        top-size (get (first args) :top default-top)
        countries-always-in-top (top/find-countries-always-meet-top top-size)]
    (log/info "Countries that are always in top" top-size "are next:" countries-always-in-top)))