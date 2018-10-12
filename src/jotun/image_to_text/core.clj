(ns jotun.image-to-text.core
  (:require [clojure.spec.alpha :as s]
            [config.core :refer [env]]
            [jotun.core :as jotun])
  (:gen-class))

(s/def ::type string?)
(s/def ::body string?)
(s/def ::phrase boolean?)
(s/def ::case boolean?)
(s/def ::numeric boolean?)
(s/def ::math int?)
(s/def ::minLength int?)
(s/def ::maxLength int?)
(s/def ::task (s/keys :req-un [::type ::body]
                      :opt-un [::phrase ::case ::numeric ::math ::minLength ::maxLength]))

(defn- parse-result
  [jotun-result]
  (let [res (:result jotun-result)
        solution (:solution res)]
    {:cost (:cost res)
     :ip (:ip res)
     :url (:url solution)
     :solution (:text solution)
     :create-time (:createTime res)
     :end-time (:endTime res)}))


(defn- get-result
  "Function to ask for actively ask for result from the Anti-Captcha service."
  [task-id & {:keys [num-tries wait-time] :or {num-tries 5 wait-time 1000}}]
  (let [result-url (str (:anti-captcha-url env) "getTaskResult")
        resp (jotun/jotun-post result-url {:clientKey (:api-key env) :taskId task-id})]
    (if (:error? resp)
      (:error resp)
      (if (or (= (:status (:result resp)) "ready") (= 0 num-tries))
        (parse-result resp)
        (do
          (Thread/sleep wait-time)
          (recur task-id (- num-tries 1)))))))

(defn solve
  "Function to solve the "
  [body & {:keys [phrase case numeric math minLength maxLength]}]
  (let [tsk1 {:body body :type "ImageToTextTask"}
        task (assoc tsk1 :phrase phrase :case case :numeric numeric :math math
                    :minLength minLength :maxLength maxLength)
        pos-task (into {} (filter second task))]
    (if (s/valid? ::task pos-task)
      (let [resp (jotun/send-captcha pos-task)
            task-id (-> resp :result :taskId)]
        (get-result task-id))
      (throw (AssertionError. "You have a bad formatted task.")))))
