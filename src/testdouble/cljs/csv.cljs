(ns testdouble.cljs.csv
  (:require [clojure.string :as str]))

(defn- ->csv [data separator]
  (str/join separator data))

(defn- write-data [data separator]
  (str/join "\n" (map #(->csv % separator) data)))

(defn write-csv
  "Writes data to String in CSV-format.

  Valid options are
    :separator (default ,) "
  [data & options]
  (let [{:keys [separator] :or {separator ","}} options]
    (write-data data separator)))
