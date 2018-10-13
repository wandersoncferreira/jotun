(ns jotun.utils-test
  (:require [clojure.test :refer :all]
            [jotun.image-to-text.convert :refer :all]))

(deftest convert-from-url-test
  (testing "get base64"
    (let [url "https://2captcha.com/template/new/img/captcha.png"
          res (convert-image-from-url url)
          beg-base64 (apply str (take 25 res))]
      (is (= "iVBORw0KGgoAAAANSUhEUgAAA" beg-base64)))))

