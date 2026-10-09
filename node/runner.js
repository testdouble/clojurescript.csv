// Runs a compiled test build in a fresh context (no Node globals such as
// `process`, which collide with Closure namespaces) and exits non-zero on failure.
var fs = require('fs');
var vm = require('vm');

if (process.argv.length !== 3) {
  console.log('Expected a compiled test file parameter.');
  process.exit(1);
}

var context = vm.createContext({ console: console });
vm.runInContext(fs.readFileSync(process.argv[2], 'utf8'), context);

var passed = vm.runInContext('testdouble.cljs.csv_test.run()', context);
process.exit(passed ? 0 : 1);
