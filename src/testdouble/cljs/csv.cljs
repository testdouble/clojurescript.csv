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

  Accepts the following options:

  :separator - field seperator
               (default ,)

  :newline   - line seperator
               (accepts :lf or :cr+lf)
               (default :lf)"

  [data & options]
  (let [{:keys [separator newline] :or {separator "," newline :lf}} options]
    (if-let [newline-char (get newlines newline)]
      (write-data data
                  separator
                  newline-char)
      (throw (js/Error. newline-error-message)))))
