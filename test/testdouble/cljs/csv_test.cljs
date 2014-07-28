(ns testdouble.cljs.csv-test
  (:require [testdouble.cljs.csv :as csv]
            [cemerick.cljs.test :as t])
  (:require-macros [cemerick.cljs.test :refer [deftest testing is]]))

(deftest write-csv-test
  (let [data [[1 2 3] [4 5 6]]]
    (testing "default separator ','"
      (is (= "1,2,3\n4,5,6" (csv/write-csv data))))

    (testing "user defined separator '|'"
      (is (= "1|2|3\n4|5|6" (csv/write-csv data :separator "|"))))))
