(defproject testdouble/clojurescript.csv "0.1.0-SNAPSHOT"
  :description "A ClojureScript library for reading and writing comma separated values"
  :url "https://github.com/testdouble/clojurescript.csv"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2277"]]
  :plugins [[lein-cljsbuild "1.0.3"]]
  :cljsbuild {:builds [{:source-paths ["src"], :id "main", :jar true}]})
