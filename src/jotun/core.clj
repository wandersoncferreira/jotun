(ns jotun.core
  (:require [clj-http.client :as http]
            [config.core :refer [env]]))

(defrecord JotunResult
    [result error? error])

(defn jotun-post
  "Boilerplate for post methods. Return body or error."
  [url body]
  (let [resp (:body (http/post url
                               {:form-params body
                                :content-type :json
                                :as :json}))
        error (:errorId resp)]
    (if (= error 0)
      (JotunResult. resp false "")
      (JotunResult. false true (:errorDescription resp)))))

(defn send-captcha
  "Function to send a captcha to Anti-captcha."
  [task & {:keys [callback-url language-pool soft-id]}]
  (let [task-url (str (:anti-captcha-url env) "createTask")
        prep-body {:clientKey (:api-key env) :task task}
        body (assoc prep-body :callbackUrl callback-url :languagePool language-pool)
        pos-body (into {} (filter second body))]
    (jotun-post task-url pos-body)))
