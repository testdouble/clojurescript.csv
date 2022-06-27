(ns testdouble.cljs.csv-test
  (:require [testdouble.cljs.csv :as csv]
            [cljs.test :as t])
  (:require-macros [cljs.test :refer [deftest testing is run-tests]]))

(enable-console-print!)

(deftest write-csv-test
  (let [data [[1 2 3] [4 5 6]]]
    (testing "default separator ','"
      (is (= "1,2,3\n4,5,6" (csv/write-csv data))))

    (testing "user defined separator '|'"
      (is (= "1|2|3\n4|5|6" (csv/write-csv data :separator "|"))))

    (testing "user defined newline ':cr+lf'"
      (is (= "1,2,3\r\n4,5,6" (csv/write-csv data :newline :cr+lf))))

    (testing "user defined separator '|' and newline ':cr+lf"
      (is (= "1|2|3\r\n4|5|6" (csv/write-csv data :separator "|" :newline :cr+lf))))

    (testing "quote each field"
      (is (= "\"1,000\",\"2\",\"3\"\n\"4\",\"5,000\",\"6\"" (csv/write-csv [["1,000" "2" "3"] ["4" "5,000" "6"]] :quote? true))))

    (testing "str non-string fields"
      (is (= "100,2,\n4,500,false"
             (csv/write-csv [["100" 2 nil] ["4" "500" false]]))))

    (testing "str and quote non-string fields"
      (is (= "\"1,000\",\"2\",\"\"\n\"4\",\"5,000\",\"false\""
             (csv/write-csv [["1,000" 2 nil] ["4" "5,000" false]] :quote? true))))

    (testing "valid characters in quoted fields"
      (is (= "\"a\nb\",\"c\rd\"\n\"e,f\",\"g\"\"h\""
             (csv/write-csv [["a\nb" "c\rd"] ["e,f" "g\"h"]] :quote? true))))

    (testing "fields with spaces"
      (is (= "a b,c d\ne f,g h"
             (csv/write-csv [["a b" "c d"] ["e f" "g h"]])))
      (is (= "\"a b\",\"c d\"\n\"e f\",\"g h\""
             (csv/write-csv [["a b" "c d"] ["e f" "g h"]] :quote? true))))

    (testing "blank fields at end of row"
      (is (= "a,b,c\n1,1,1\n2,,\n3,,"
             (csv/write-csv [["a" "b" "c"]
                             ["1" "1" "1"]
                             ["2" "" ""]
                             ["3" "" ""]]))))

    (testing "error when newline is not one of :lf OR :cr+lf"
      (is (thrown-with-msg? js/Error #":newline" (csv/write-csv data :newline "foo"))))

    (testing "auto quote when quote? is false"
      (is (= "a,\"b, b\",c\n1,2,\"3,4,5\""
             (csv/write-csv [["a", "b, b", "c"]
                             ["1", "2", "3,4,5"]])))
      (is (= "a|b, b|c\n1|2|\"3|4|5\""
             (csv/write-csv [["a", "b, b", "c"]
                             ["1", "2", "3|4|5"]]
                            :separator "|"))))))

(deftest read-csv-test
  (let [data [["1" "2" "3"] ["4" "5" "6"]]]
    (testing "default separator ','"
      (is (= data (csv/read-csv "1,2,3\n4,5,6"))))

    (testing "user defined separator '|'"
      (is (= data (csv/read-csv "1|2|3\n4|5|6" :separator "|"))))

    (testing "user defined newline ':cr+lf'"
      (is (= data (csv/read-csv "1,2,3\r\n4,5,6" :newline :cr+lf))))

    (testing "user defined separator '|' and newline ':cr+lf'"
      (is (= data (csv/read-csv "1|2|3\r\n4|5|6" :separator "|" :newline :cr+lf))))

    (testing "lone carriage return within ':cr-lf' newlines"
      (is (= [["1" "\r2" "3"] ["4" "5" "6"]] (csv/read-csv "1,\r2,3\r\n4,5,6" :newline :cr+lf))))

    (testing "lone newline within ':cr-lf' newlines"
      (is (= [["1" "\n2" "3"] ["4" "5" "6"]] (csv/read-csv "1,\n2,3\r\n4,5,6" :newline :cr+lf))))

    (testing "empty data"
      (is (= [[""]]
             (csv/read-csv ""))))

    (testing "nearly empty data"
      (is (= [["a"]]
             (csv/read-csv "a"))))

    (testing "longer data"
      (is (= [["one" "two" "three"] ["uno" "dos" "tres"] ["un" "deux" "trois"]]
             (csv/read-csv "one,two,three\nuno,dos,tres\nun,deux,trois"))))

    (testing "valid characters in quoted fields"
      (is (= [["a\nb" "c\rd"] ["e,f" "g\"h"]]
             (csv/read-csv "\"a\nb\",\"c\rd\"\n\"e,f\",\"g\"\"h\""))))

    (testing "quoted fields containing only quotes"
      (is (= [["\"" "\"\""] ["\"\"\"" "\"\"\"\""]]
             (csv/read-csv "\"\"\"\",\"\"\"\"\"\"\n\"\"\"\"\"\"\"\",\"\"\"\"\"\"\"\"\"\""))))

    (testing "quote in middle of unquoted field"
      (is (= [["a\"b" "c"]]
             (csv/read-csv "a\"b,c"))))

    (testing "fields with spaces"
      (is (= [["a b" "c d"] ["e f" "g h"]]
             (csv/read-csv "\"a b\",c d\ne f,\"g h\""))))

    (testing "empty header"
      (is (= [["" "" ""] ["1" "2" "3"]]
             (csv/read-csv ",,\n1,2,3"))))

    (testing "empty fields"
      (is (= [["a" "b" "c" "d"] ["1" "" "" "d"]]
             (csv/read-csv "a,b,c,d\n1,\"\",,d"))))

    (testing "blank fields at beginning of row"
      (is (= [["a" "b"]
              ["" "1"]
              ["" ""]]
             (csv/read-csv "a,b\n,1\n,"))))

    (testing "blank fields at end of row"
      (is (= [["a" "b" "c"]
              ["1" "1" "1"]
              ["2" "" ""]
              ["3" "" ""]]
             (csv/read-csv "a,b,c\n1,1,1\n2,,\n3,,"))))

    (testing "trailing newline"
      (is (= [["a" "b" "c"] [""]]
             (csv/read-csv "a,b,c\n"))))

    (testing "no columns"
      (is (= [[""]
              ["a b c"]
              [""]]
             (csv/read-csv "\na b c\n"))))))

(defn ^:export run []
  (run-tests))
