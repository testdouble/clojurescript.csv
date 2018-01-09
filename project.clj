(defproject testdouble/clojurescript.csv "0.3.1-SNAPSHOT"
  :description "A ClojureScript library for reading and writing comma (and other) separated values."
  :url "https://github.com/testdouble/clojurescript.csv"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2511"]]
  :plugins [[lein-cljsbuild "1.1.0"]]
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
              :test-commands {; PhantomJS tests
                              "phantom-whitespace" ["phantomjs" "phantom/runner.js" "test-resources/html/whitespace.html"]
                              "phantom-simple" ["phantomjs" "phantom/runner.js" "test-resources/html/simple.html"]
                              ;; "phantom-advanced" ["phantomjs" "phantom/runner.js" "test-resources/html/advanced.html"]
                              }}
  :aliases {"cleantest" ["do" "clean," "test"]
            "release" ["do" "clean," "deploy" "clojars"]})
