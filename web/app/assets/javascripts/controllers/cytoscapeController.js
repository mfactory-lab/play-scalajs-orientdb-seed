/**
 * Copyright 2016, Alexander Ray (dev@alexray.me)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 **/

define(['angular', 'cytoscape'], function (angular, cytoscape) {
    'use strict';

    var mod = angular.module('web.visualise.cytoscape', []);
    mod.controller('cytoscapeController', ['$scope', '$log', 'orientDbService',
        function ($scope, $log, orientDbService) {

            $scope.graphLoaded = false;

            $scope.drawGraph = function () {

                if (!$scope.graphLoaded) {
                    $scope.graphLoaded = true;

                    orientDbService.loadGraph().then(drawGraph, function (error) {
                            $log.error(error);
                            $scope.graphLoaded = false;
                        }
                    )
                }

            };

            var drawGraph = function (graph) {
                var vertices = graph.vertices;
                var edges = graph.edges;

                var elements = [];

                for (var i = 0; i < vertices.length; i++) {
                    var vertex = vertices[i];
                    var velem = {
                        data: {
                            id: vertex.rid,
                            name: vertex.name,
                            age: vertex.age,
                            label: vertex.rid + "\n" + vertex.name + '\n' + vertex.age
                        }
                    };
                    elements.push(velem);
                }

                for (var j = 0; j < edges.length; j++) {
                    var edge = edges[j];
                    var eelem = {
                        data: {
                            id: edge.rid,
                            source: edge.out.rid,
                            target: edge.in.rid,
                            label: "knows " + edge.since
                        }
                    };
                    elements.push(eelem);
                }

                var cy = cytoscape({
                    container: document.getElementById('cy'),

                    elements: elements,

                    style: [ // the stylesheet for the graph
                        {
                            selector: 'node',
                            style: {
                                'shape': 'roundrectangle',
                                'border-width': '1',
                                'border-color': '#33b5e5',
                                'background-color': '#ffbb33',
                                'label': 'data(label)',
                                'width': '25',
                                'height': '25',
                                'font-size': 6,
                                'text-wrap': "wrap",
                                'text-halign': 'center',
                                'text-valign': 'center'
                            }
                        },

                        {
                            selector: 'edge',
                            style: {
                                'curve-style': 'bezier',
                                'width': 1,
                                'line-color': '#1eabcd',
                                'target-arrow-color': '#1eabcd',
                                'target-arrow-shape': 'triangle',
                                'label': 'data(label)',
                                'font-size': 4,
                                'edge-text-rotation': 'autorotate',
                                'text-outline-color': '#fff',
                                'text-outline-opacity': '1',
                                'text-outline-width': '1'

                            }
                        }
                    ],

                    layout: {
                        name: 'breadthfirst',

                        fit: true,
                        directed: false,
                        padding: 10,
                        circle: true,
                        spacingFactor: 1.0,
                        avoidOverlap: true,
                        maximalAdjustments: 0
                    }

                });

            };

            return {};
        }
    ]);

    return mod;

});