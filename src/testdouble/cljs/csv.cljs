(ns testdouble.cljs.csv
  (:require [clojure.string :as str]))

(defn- escape-quotes [s]
  (str/replace s "\"" "\"\""))

(defn- wrap-in-quotes [s]
  (str "\"" (escape-quotes s) "\""))

(defn- seperate [data separator quote?]
  (str/join separator
            (cond->> data
              quote? (map wrap-in-quotes))))

(defn- write-data [data separator newline quote?]
  (str/join newline (map #(seperate % separator quote?) data)))

(defn- conj-in [coll index x]
  (assoc coll index (conj (nth coll index) x)))

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
               (default :lf)
  :quote?    - wrap in quotes
               (default false)"

  {:arglists '([data] [data & options]) :added "0.1.0"}
  [data & options]
  (let [{:keys [separator newline quote?] :or {separator "," newline :lf quote? false}} options]
    (if-let [newline-char (get newlines newline)]
      (write-data data
                  separator
                  newline-char
                  quote?)
      (throw (js/Error. newline-error-message)))))

(defn read-csv
  "Reads data from String in CSV-format."
  {:arglists '([data] [data & options]) :added "0.3.0"}
  [data & options]
  (let [{:keys [separator newline] :or {separator "," newline :lf}} options]
    (if-let [newline-char (get newlines newline)]
      (let [data-length (count data)]
        (loop [index 0
               state :in-field
               in-quoted-field false
               field-buffer nil
               rows []]
          (let [last-row-index (- (count rows) 1)]
            (if (< index data-length)
              (let [char (nth data index)
                    next-char (if (< index (- data-length 1))
                                (nth data (+ index 1))
                                nil)
                    str-char (str char)]
                (case state
                  :in-field
                  (cond
                    (= str-char "\"")
                    (if in-quoted-field
                      (if (= (str next-char) "\"")
                        (recur (+ index 2)
                               :in-field
                               true
                               (str field-buffer char)
                               rows)
                        (recur (+ index 1) :in-field false field-buffer rows))
                      (recur (+ index 1)
                             :in-field
                             true
                             field-buffer
                             (if (> (count rows) 0)
                               rows
                               (conj rows []))))

                    (= str-char separator)
                    (if in-quoted-field
                      (recur (+ index 1)
                             :in-field
                             in-quoted-field
                             (str field-buffer char)
                             rows)
                      (recur (+ index 1)
                             :end-field
                             in-quoted-field
                             ""
                             (conj-in rows last-row-index field-buffer)))

                    (= str-char "\r")
                    (if (and (= newline :cr+lf) (not in-quoted-field))
                      (recur (+ index 1)
                             :in-field
                             in-quoted-field
                             field-buffer
                             rows)
                      (recur (+ index 1)
                             :in-field
                             in-quoted-field
                             (str field-buffer char)
                             rows))

                    (= str-char "\n")
                    (if in-quoted-field
                      (recur (+ index 1)
                             :in-field
                             in-quoted-field
                             (str field-buffer char)
                             rows)
                      (recur (+ index 1)
                             :end-line
                             in-quoted-field
                             ""
                             (conj-in rows last-row-index field-buffer)))

                    :else
                    (recur (+ index 1)
                           :in-field
                           in-quoted-field
                           (str field-buffer char)
                           (if (> (count rows) 0)
                             rows
                             (conj rows []))))

                  :end-field
                  (cond 
                    (= str-char "\"")
                    (recur (+ index 1) :in-field true field-buffer rows)

                    (= str-char separator)
                    (recur (inc index) :end-field in-quoted-field "" (conj-in rows last-row-index ""))

                    :else (recur (+ index 1) :in-field in-quoted-field str-char rows))

                  :end-line
                  (case str-char
                    "\""
                    (recur (+ index 1)
                           :in-field
                           true
                           field-buffer
                           (conj (or rows []) []))

                    (recur (+ index 1)
                           :in-field
                           in-quoted-field
                           (str field-buffer char)
                           (conj (or rows []) [])))))
              (conj-in rows last-row-index field-buffer)))))
      (throw (js/Error. newline-error-message)))))

