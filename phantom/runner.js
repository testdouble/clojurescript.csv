if (phantom.args.length != 1) {
  console.log('Expected a target URL parameter.');
  phantom.exit(1);
}

var page = require('webpage').create();
var url = phantom.args[0];

page.onConsoleMessage = function (message) {
  console.log("Test console: " + message);
};

console.log("Loading URL: " + url);

page.open(url, function (status) {
  if (status != "success") {
    console.log('Failed to open ' + url);
    setTimeout(function() { // https://github.com/ariya/phantomjs/issues/12697
      phantom.exit(1);
    }, 0);
  }

  console.log("Running test.");

  var result = page.evaluate(function() {
    return testdouble.cljs.csv_test.run();
  });

  var failures = result.arr[1];
  if (failures !== 0) {
    console.log("*** Test failed! ***");
    setTimeout(function() { // https://github.com/ariya/phantomjs/issues/12697
      phantom.exit(1);
    }, 0);
  }

  var errors = result.arr[3];
  if (errors !== 0) {
    console.log("*** Test errored! ***");
    setTimeout(function() { // https://github.com/ariya/phantomjs/issues/12697
      phantom.exit(1);
    }, 0);
  }

  console.log("Test succeeded.");
  setTimeout(function() { // https://github.com/ariya/phantomjs/issues/12697
    phantom.exit(0);
  }, 0);
});
