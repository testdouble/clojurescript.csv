(ns testdouble.cljs.csv-test
  (:require [testdouble.cljs.csv :as csv]
            [cemerick.cljs.test :as t])
  (:require-macros [cemerick.cljs.test :refer [deftest is]]))

(deftest write-csv-test
  (is (= "1,2,3\n4,5,6" (csv/write-csv [[1 2 3] [4 5 6]]))))
