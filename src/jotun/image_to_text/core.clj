(ns jotun.image-to-text.core
  (:require [clojure.spec.alpha :as s]
            [config.core :refer [env]]
            [jotun.core :as jotun]
            [jotun.image-to-text.convert :as j-convert])
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

(defn- get-right-base64
  "`solve` function can receive either a direct base64 image or the filepath
  for a image. Let's figure it out which one we got. I would like to keep the
  interface of the `solve` function unchanged."
  [undefined-var]
  (if (.isFile (clojure.java.io/file undefined-var))
    (j-convert/convert-image-to-base64 undefined-var)
    undefined-var))

;; TODO: add support to receive a link to download the image captcha
;; improve the inner interface to handle all of these subtle differences.
(defn solve
  "Function to solve image captchas. It can either receive a direct base64 from
  an image or the filepath to an image on disk."
  [undefined-input & {:keys [phrase case numeric math minLength maxLength]}]
  (let [tsk1 {:body (get-right-base64 undefined-input) :type "ImageToTextTask"}
        task (assoc tsk1 :phrase phrase :case case :numeric numeric :math math
                    :minLength minLength :maxLength maxLength)
        pos-task (into {} (filter second task))]
    (if (s/valid? ::task pos-task)
      (let [resp (jotun/send-captcha pos-task)
            task-id (-> resp :result :taskId)]
        (get-result task-id))
      (throw (AssertionError. "You have a bad formatted task.")))))



