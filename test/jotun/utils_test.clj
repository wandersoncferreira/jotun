(ns jotun.utils-test
  (:require [clojure.test :refer :all]
            [jotun.utils :refer :all]))

(deftest stats-description-test
  (testing "If all the descriptions are available in the map"
    (is (= [:waiting :load
            :bid :speed :total] (keys stats-map)))))

(deftest stats-queue-test
  (testing "The answer from the API were true, so there must be these keys."
    (is (= [:waiting :load
            :bid :speed :total] (keys (get-queue-stats))))))

(deftest stats-queue-values-test
  (testing "Verify numeric numbers in the answer from the API."
    (is (->> (get-queue-stats)
             (map #(:value (second %)))
             (every? number?)))))

(deftest balance-test
  (testing "The call to the server has to work and return a number."
    (is (number? (get-balance)))))
