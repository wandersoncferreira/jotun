(defproject jotun "0.3.1"
  :description "Package to deal with Anti-Captcha libraries in Clojure."
  :url "http://github.com/wandersoncferreira/jotun"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-http "3.9.1"]
                 [cheshire "5.8.1"]
                 [yogthos/config "1.1.1"]
                 [org.clojure/data.codec "0.1.1"]
                 [com.taoensso/timbre "4.10.0"]]
  :profiles {:prod {:resource-paths ["config/prod"]}
             :dev {:resource-paths ["config/dev"]}})
