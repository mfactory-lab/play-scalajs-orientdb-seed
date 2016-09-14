/**
 * Copyright 2016, Alexander Ray (dev@alexray.me)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 **/

module.exports = function (config) {

    var target = './web/target/web/public/test/public/';

    var cfg = {

        basePath: '../../../',

        frameworks: ['jasmine'],

        browsers: ['Chrome'],

        singleRun: true,

        files: [

            // jsRoutes
            './web/target/web/jsrouter/jsRoutes.js',

            // requirejs
            './node_modules/requirejs/require.js',
            './node_modules/karma-requirejs/lib/adapter.js',

            // requirejs config
            './web/test/midway/test-main.js',

            // will be loaded with requirejs
            // scalajsApp
            {pattern: target + 'scalajs-opt.js', included: false},
            // libs
            {pattern: target + 'lib/jquery/jquery.js', included: false},
            {pattern: target + 'lib/angular/angular.js', included: false},
            {pattern: target + 'lib/angular-mocks/angular-mocks.js', included: false},
            {pattern: target + 'lib/cytoscape/dist/cytoscape.js', included: false},
            // assets
            {pattern: target + 'javascripts/common/services/playRoutesService.js', included: false},
            {pattern: target + 'javascripts/controllers/cytoscapeController.js', included: false},
            // specs
            {pattern: './web/test/midway/*Spec.js', included: false}

        ],

        plugins: [
            'karma-chrome-launcher',
            'karma-jasmine'
        ]

    };

    config.set(cfg);
};
