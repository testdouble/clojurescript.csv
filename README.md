# clojurescript.csv [![Build Status](https://travis-ci.org/testdouble/clojurescript.csv.png?branch=master)](https://travis-ci.org/testdouble/clojurescript.csv) [![Dependency Status](https://www.versioneye.com/user/projects/53d67fe23648f4a793000046/badge.svg)](https://www.versioneye.com/user/projects/53d67fe23648f4a793000046)

A ClojureScript library for reading and writing comma (and other) separated values.

## Installation

[Leiningen](https://github.com/technomancy/leiningen/):

```
[testdouble/clojurescript.csv "0.4.6"]
```

[Maven](http://maven.apache.org/):

```
<dependency>
  <groupId>testdouble</groupId>
  <artifactId>clojurescript.csv</artifactId>
  <version>0.4.6</version>
</dependency>
```

## Usage

```clojure
(ns my.domain.core
  (:require [testdouble.cljs.csv :as csv]))

;; Basic usage
(csv/write-csv [[1 2 3] [4 5 6]])
;;=> "1,2,3\n4,5,6"

;; Define your own separator
(csv/write-csv [[1 2 3] [4 5 6]] :separator "|")
;;=> "1|2|3\n4|5|6"

;; Use either :lf (default) or :cr+lf as the newline character
(csv/write-csv [[1 2 3] [4 5 6]] :newline :cr+lf)
;;=> "1,2,3\r\n4,5,6"

;; Quote all fields
(csv/write-csv [[1000 2.9 true
                 "description, with comma"]
                [4 false "6,000"
				"prior is a quoted number with , thousands delimiter"]] 
                :quote? true)
;;=> ""1000","2.9","true","description, with comma"\n"4","false","6,000","prior is a quoted number with , thousands delimiter""

;; But keep-numbers unquoted
;; note 1000, 2.9 and 4 in output are not individually quoted.
(csv/write-csv [[1000 2.9 true
                 "description, with comma"]
                [4 false "6,000"
				"prior is a quoted number with , thousands delimiter"]] 
                :quote? true :keep-numbers? true)
;;=> "1000,2.9,"true","description, with comma"\n4,"false","6,000","prior is a quoted number with , thousands delimiter""
```

## Development

Running the tests:

```
$ lein cleantest
```

(assumes Leiningen 2.x, requires [PhantomJS](https://phantomjs.org))

## Contributing

Thanks to all of our [contributors](https://github.com/testdouble/clojurescript.csv/graphs/contributors).

We welcome all contributors to the project. Please submit an [Issue](https://github.com/testdouble/clojurescript.csv/issues)
and a
[Pull Request](https://github.com/testdouble/clojurescript.csv/pulls)
if you have one.

## License

Copyright © 2014 Test Double

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
