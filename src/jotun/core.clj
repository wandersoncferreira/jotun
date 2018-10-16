(ns jotun.core
  (:require [clj-http.client :as http]
            [config.core :refer [env load-env]]
            [jotun.logs :as logs]
            [taoensso.timbre :as timbre])
  (:gen-class))

(logs/jotun-config-log)

(defrecord JotunResult
    [result error? error])

(defn new->jotun-result
  [& {:keys [result error? error-msg url status-code] :or {result nil error? nil
                                                           url nil error-msg nil
                                                           status-code nil}}]
  (if error?
    (timbre/error (str url ":  <error> " error-msg))
    (timbre/info (str url ":  <status-code> " status-code)))
  (JotunResult. result error? error-msg))

(defn jotun-post
  "Boilerplate for post methods. Return body or error."
  [url body]
  (let [resp (http/post url
                        {:form-params body
                         :content-type :json
                         :as :json})
        body (:body resp)
        error (:errorId body)]
    (println resp)
    (if (or (= error 0) (nil? error))
      (new->jotun-result :result body :error? false :url url :status-code (:status resp))
      (new->jotun-result :error? true :error-msg (:errorDescription body) :url url))))

(defn send-captcha
  "Function to send a captcha to Anti-captcha."
  [task & {:keys [callback-url language-pool soft-id]}]
  (let [task-url (str (:anti-captcha-url env) "createTask")
        prep-body {:clientKey (:apikey env) :task task}
        body (assoc prep-body :callbackUrl callback-url :languagePool language-pool)
        pos-body (into {} (filter second body))]
    (jotun-post task-url pos-body)))


