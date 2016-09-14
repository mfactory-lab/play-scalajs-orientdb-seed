/**
 * Copyright 2016, Alexander Ray (dev@alexray.me)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 **/

requirejs.config({
    map: {
        '*': {
            'css': '/webjars/require-css/0.1.8/css.js' // or whatever the path to require-css is

        }
    },

    shim: {
        'jsRoutes': {
            deps: [],
            exports: 'jsRoutes'
        },

        'angular': {
            exports: 'angular'
        },

        'jquery': {
            exports: 'jquery'
        },

        'bootstrap': {
            deps: ['jquery']
        },

        'cytoscape': {
            exports: 'cytoscape'
        }
    },

    paths: {
        'angular': ['/assets/lib/angular/angular'],
        'jsRoutes': ['/jsroutes'],
        'scalaJsApp': [
            function () {
                if (window.isProduction) {
                    return '/assets/scalajs-opt'
                } else {
                    return '/assets/scalajs-fastopt'
                }
            }()

        ],
        'bootstrap': ['/assets/lib/bootstrap/js/bootstrap.min'],
        'jquery': ['/assets/lib/jquery/jquery.min'],
        'cytoscape': ['/assets/lib/cytoscape/dist/cytoscape.min']
    }

});

requirejs([
    'angular',
    'scalaJsApp',
    './common/services/playRoutesService',
    'cytoscape',
    './controllers/cytoscapeController',
    'bootstrap'
], function (angular) {
    ((typeof global === "object" && global && global["Object"] === Object) ? global : this)["App"]().main();

    angular.bootstrap(document, ["test-module.scalajs-app", "web.visualise.cytoscape"]);

});