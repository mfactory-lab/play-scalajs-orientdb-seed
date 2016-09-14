/**
 * Copyright 2016, Alexander Ray (dev@alexray.me)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 **/

var TEST_REGEXP = /(spec|test)\.js$/i;
var allTestFiles = [];

// Get a list of all the test files to include
Object.keys(window.__karma__.files).forEach(function(file) {
    if (TEST_REGEXP.test(file)) {
        // Normalize paths to RequireJS module names.
        // If you require sub-dependencies of test files to be loaded as-is (requiring file extension)
        // then do not normalize the paths
        var normalizedTestModule = file.replace(/^\/base\/|\.js$/g, '');
        allTestFiles.push(normalizedTestModule);
    }
});

require.config({
    // Karma serves files under /base, which is the basePath from config file
    baseUrl: '/base',

    paths: {
        'angular': ['./web/target/web/public/test/public/lib/angular/angular'],
        'angular-mocks': ['./web/target/web/public/test/public/lib/angular-mocks/angular-mocks'],
        'jquery': ['./web/target/web/public/test/public/lib/jquery/jquery'],
        'cytoscape': ['./web/target/web/public/test/public/lib/cytoscape/dist/cytoscape'],
        'jsRoutes': ['./web/target/web/jsrouter/jsRoutes'],
        'playRoutesService': ['./web/target/web/public/test/public/javascripts/common/services/playRoutesService'],
        'cytoscapeController': ['./web/target/web/public/test/public/javascripts/controllers/cytoscapeController'],
        'scalaJsApp': ['./web/target/web/public/test/public/scalajs-opt']
    },

    // load non AMD libraries
    shim: {
        'jsRoutes': {
            deps: [],
            exports: 'jsRoutes'
        },

        'angular': {
            exports: 'angular'
        },

        'cytoscape': {
            exports: 'cytoscape'
        },

        'angular-mocks': {
            deps: ['angular']
        }
    },

    // dynamically load all test files
    deps: allTestFiles
});

require([
    'angular',
    'angular-mocks',
    'jquery',
    'scalaJsApp',
    'playRoutesService',
    'cytoscape',
    'cytoscapeController'
], function (angular)
{
    // start ScalaJs app
    ((typeof global === "object" && global && global["Object"] === Object) ? global : this)["App"]().main();

    // bootstrap angular modules
    angular.bootstrap(document, ["test-module.scalajs-app", "web.visualise.cytoscape"]);

    // we have to kickoff jasmine, as it is asynchronous
    window.__karma__.start();

});