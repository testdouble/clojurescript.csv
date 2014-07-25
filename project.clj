(defproject testdouble/clojurescript.csv "0.1.0-SNAPSHOT"
  :description "A ClojureScript library for reading and writing comma separated values"
  :url "https://github.com/testdouble/clojurescript.csv"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2277"]
                 [com.cemerick/clojurescript.test "0.3.1"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [com.cemerick/clojurescript.test "0.3.1"]]
  :cljsbuild {:builds [{:source-paths ["src" "test"]
                        :compiler {:output-to "target/cljs/whitespace.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/cljs/simple.js"
                                   :optimizations :simple
                                   :pretty-print true}}
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/cljs/advanced.js"
                                   :optimizations :advanced
                                   :pretty-print true}}]
              :test-commands {; PhantomJS tests
                              "phantom-whitespace" ["phantomjs" :runner "target/cljs/whitespace.js"]
                              "phantom-simple" ["phantomjs" :runner "target/cljs/simple.js"]
                              "phantom-advanced" ["phantomjs" :runner "target/cljs/advanced.js"]}}
  :aliases {"cljsrepl" ["exec" "-ep" "(cemerick.austin.repls/exec)"]})
