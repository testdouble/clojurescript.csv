(defproject testdouble/clojurescript.csv "0.2.0-SNAPSHOT"
  :description "A ClojureScript library for reading and writing comma separated values"
  :url "https://github.com/testdouble/clojurescript.csv"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2505"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [com.cemerick/austin "0.1.4"]]
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
  :aliases {"cljsrepl" ["exec" "-ep" "(cemerick.austin.repls/exec)"]
            "cleantest" ["do" "clean," "test," "cljsbuild" "test"]
            "release" ["do" "clean," "deploy" "clojars"]})
