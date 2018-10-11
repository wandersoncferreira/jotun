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
