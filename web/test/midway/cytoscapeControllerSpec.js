/**
 * Copyright 2016, Alexander Ray (dev@alexray.me)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 **/

define([], function () {

    describe('cytoscapeController', function () {

        var $scope;
        var $q;
        var deferred;

        beforeEach(function () {
            module("test-module.scalajs-app");
            module("web.visualise.cytoscape");
        });

        beforeEach(function () {
            inject(function ($controller, _$rootScope_, _$q_, orientDbService) {
                $q = _$q_;
                $scope = _$rootScope_.$new();

                // We use the $q service to create a mock instance of defer
                deferred = _$q_.defer();

                // Use a Jasmine Spy to return the deferred promise
                spyOn(orientDbService, 'loadGraph').and.returnValue(deferred.promise);

                // Init the controller, passing our spy service instance
                $controller('cytoscapeController', {
                    $scope: $scope,
                    orientDbService: orientDbService
                });
            });
        });

        it('should put graph into scope', function () {

            // Setup the data we wish to return for the .then function in the controller
            deferred.resolve(
                {
                    vertices: [
                        {id: 1}
                    ],
                    edges: [
                        {id: 2}
                    ]
                }
            );

            // Call drawGraph for this to work
            $scope.drawGraph();

            // Since we called drawGraph, now we can perform our assertions
            expect($scope.graphLoaded).toBe(true);
        });

    });

});