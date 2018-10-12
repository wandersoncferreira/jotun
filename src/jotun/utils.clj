(ns jotun.utils
  (:require [config.core :refer [env]]
            [jotun.core :as jotun]))

(defn get-balance
  "Function to get the right value for
  the balance at the Anti-Captcha website."
  []
  (let [balance-url (str (:anti-captcha-url env) "getBalance")
        resp (jotun/jotun-post balance-url {:clientKey (:api-key env)})]
    (if (:error? resp)
      (:error resp)
      (:balance (:result resp)))))

(def stats-map {:waiting "Amount of idle workers online, waiting for a task"
                :load "Queue load in percents"
                :bid "Average task solution cost in USD"
                :speed "Average task solution speed in seconds"
                :total "Total number of workers"})

(defn get-queue-stats
  "Function to compute how the servers are on load."
  [& {:keys [queue-id] :or {queue-id 1}}]
  (let [stats-url (str (:anti-captcha-url env) "getQueueStats")
        resp (jotun/jotun-post stats-url {:queueId queue-id})]
    (->> resp
         :result
         keys
         (map (fn [k] {k {:value (k (:result resp))
                          :message (k stats-map)}}))
         (into {}))))
