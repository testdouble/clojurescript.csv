(defproject testdouble/clojurescript.csv "0.8.1-SNAPSHOT"
  :description "A ClojureScript library for reading and writing comma (and other) separated values."
  :url "https://github.com/testdouble/clojurescript.csv"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.126"]]
  :plugins [[lein-cljsbuild "1.1.8"]]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:builds [{:id "whitespace"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/js/whitespace.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}
                       {:id "simple"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/js/simple.js"
                                   :optimizations :simple
                                   :pretty-print true}}
                       {:id "advanced"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/js/advanced.js"
                                   :optimizations :advanced
                                   :pretty-print false}}]
              :test-commands {"node-whitespace" ["node" "node/runner.js" "target/js/whitespace.js"]
                              "node-simple" ["node" "node/runner.js" "target/js/simple.js"]
                              "node-advanced" ["node" "node/runner.js" "target/js/advanced.js"]}}
  :aliases {"cleantest" ["do" "clean," "test"]
            "release" ["do" "clean," "deploy" "clojars"]})
