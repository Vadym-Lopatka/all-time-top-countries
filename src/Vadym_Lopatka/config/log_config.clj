(ns Vadym-Lopatka.config.log-config
  
  (:require [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.file :as file]))

(timbre/merge-config
 {:level :info  ; Set log level threshold to info
  :appenders {:file (file/appender "logfile.log")} ; Add a file appender
  })