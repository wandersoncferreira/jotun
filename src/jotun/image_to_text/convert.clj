(ns jotun.image-to-text.convert
  (:require [clojure.data.codec.base64 :as b64]
            [clojure.java.io :as io]))

(defn convert-image-to-base64
  "Function to convert image from filepath to base64"
  [image-path]
  (->> image-path
       (clojure.java.io/file)
       (org.apache.commons.io.FileUtils/readFileToByteArray)
       (.encodeToString (java.util.Base64/getEncoder))))

;; TODO: change it to use concurrency and tail call position...
(defn- convert-coll-images
  "Conversion of a collection of images."
  [paths & {:keys [result] :or {result {}}}]
  paths
  (if (empty? paths)
    result
    (let [path (first paths)
          filename (last (clojure.string/split path #"/"))
          name (first (clojure.string/split filename #"\."))]
      (convert-coll-images (rest paths) :result (assoc result name (convert-image-to-base64 path))))))

(defn convert-image-dir-to-base64
  "Function to convert images from a directory into a base64 vector"
  [dir-path]
  (let [check-img #"\.png$|jpeg$|jpg$"
        files (file-seq (clojure.java.io/file dir-path))]
    (->> files
         (filter #(.isFile %))
         (map #(.toPath %))
         (filter #(re-find check-img (str (.getFileName %))))
         (map str)
         future-conversions)))
