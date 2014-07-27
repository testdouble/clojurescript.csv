# clojurescript.csv

A ClojureScript library for reading and writing comma separated values.

[![Build Status](https://travis-ci.org/testdouble/clojurescript.csv.png?branch=master)](https://travis-ci.org/testdouble/clojurescript.csv)

## Installation

[Leiningen](https://github.com/technomancy/leiningen/):

```
[testdouble/clojurescript.csv "0.1.0-SNAPSHOT"]
```

[Maven](http://maven.apache.org/):

```
<dependency>
  <groupId>testdouble</groupId>
  <artifactId>clojurescript.csv</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Usage

```
(ns my.domain.core
  (:require [testdouble.cljs.csv :as csv]))

(csv/write-csv [[1 2 3] [4 5 6]])
```

## Development

Running the tests:

```
$ lein cljsbuild test
```

(assumes Leiningen 2.x)

## Contributing

Thanks to all of our [contributors](https://github.com/testdouble/clojurescript.csv/graphs/contributors).

We welcome all contributors to the project. Please submit an [Issue](https://github.com/testdouble/clojurescript.csv/issues)
and a
[Pull Request](https://github.com/testdouble/clojurescript.csv/pulls)
if you have one.

## Documentation

More documentation can be found on the [wiki](https://github.com/testdouble/clojurescript.csv/wiki).

## License

Copyright © 2014 Test Double

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
