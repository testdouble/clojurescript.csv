(ns testdouble.cljs.csv
  (:require [clojure.string :as str]))

(defn- ->csv [data separator]
  (str/join separator data))

(defn- write-data [data separator newline]
  (str/join newline (map #(->csv % separator) data)))

(def ^:private newlines
  {:lf "\n" :cr+lf "\r\n"})

(def ^:private newline-error-message
  (str ":newline must be one of [" (str/join "," (keys newlines)) "]"))

(defn write-csv
  "Writes data to String in CSV-format.

  Valid options are
    :separator (default ,)"
  [data & options]
  (let [{:keys [separator newline] :or {separator "," newline :lf}} options]
    (if-let [newline-char (newlines newline)]
      (write-data data
                  separator
                  newline-char)
      (throw (js/Error. newline-error-message)))))
