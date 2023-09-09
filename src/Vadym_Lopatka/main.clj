(ns Vadym-Lopatka.main
  (:gen-class)
  (:require [Vadym-Lopatka.topcountries.top :as top]
            [taoensso.timbre :as log]))

(defn -main [& args]
  ;; (log/set-min-level! :info)
  (log/info "App is starting...")
  (let [top-size 10
        countries-always-in-top (top/find-countries-always-meet-top top-size)]
    (log/info "Countries that are always in top" top-size "are next" countries-always-in-top)))