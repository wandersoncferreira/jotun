(ns jotun.logs
  (:require [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.core :as appenders]))

(defn jotun-config-log
  []
  (let [log-file-name "logs.txt"]
    (timbre/merge-config! {:appenders {:println {:enabled? false}
                                       :spit (appenders/spit-appender {:fname log-file-name})}})))
