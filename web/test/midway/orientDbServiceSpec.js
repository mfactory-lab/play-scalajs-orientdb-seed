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

    describe('orientDbService', function () {

        var orientDbService, httpBackend;

        beforeEach(function () {
            module("test-module.scalajs-app");
        });

        beforeEach(function () {
            inject(function (_orientDbService_, $httpBackend) {
                orientDbService = _orientDbService_;
                httpBackend = $httpBackend;
            });
        });

        afterEach(function () {
            httpBackend.verifyNoOutstandingRequest();
            httpBackend.verifyNoOutstandingExpectation();
        });

        it('loadGraph() method should return graph', function () {

            var httpResponseMock = {
                "vertices": {
                    "#elems": [
                        {
                            "#id": "1",
                            "rid": "#15:0",
                            "name": "Alice",
                            "age": 20
                        }, {
                            "#id": "2",
                            "rid": "#15:1",
                            "name": "Charlie",
                            "age": 40
                        }, {
                            "#id": "3",
                            "rid": "#16:0",
                            "name": "Bob",
                            "age": 30
                        }, {
                            "#id": "4",
                            "rid": "#16:1",
                            "name": "Diana",
                            "age": 50
                        }]
                },
                "edges": {
                    "#elems": [{
                        "rid": "#17:0",
                        "in": {"#ref": "2"},
                        "#id": "6",
                        "out": {"#ref": "1"},
                        "since": "since March 2010"
                    }, {
                        "rid": "#17:1",
                        "in": {"#ref": "1"},
                        "#id": "7",
                        "out": {"#ref": "4"},
                        "since": "since January 2012"
                    }, {
                        "rid": "#18:1",
                        "in": {"#ref": "4"},
                        "#id": "8",
                        "out": {"#ref": "2"},
                        "since": "since February 2015"
                    }, {
                        "rid": "#18:0",
                        "in": {"#ref": "4"},
                        "#id": "9",
                        "out": {"#ref": "1"},
                        "since": "since January 2012"
                    }, {
                        "rid": "#17:2",
                        "in": {"#ref": "2"},
                        "#id": "10",
                        "out": {"#ref": "3"},
                        "since": "since June 2000"
                    }]
                }
            };

            function whenSuccess(graph) {

                expect(graph).toBeDefined();

                var vertices = graph.vertices;
                expect(vertices.length).toBe(httpResponseMock.vertices.length);

                var edges = graph.edges;
                expect(edges.length).toBe(httpResponseMock.edges.length);

            }

            function whenError(error) {
                console.error(error);
                // manually failing test
                expect(true).toBe(false);
            }

            function whenFinally() {
                done();
            }

            // use $httpBackend to retrun a mocked value of pickled graph
            httpBackend.whenPOST("/api/loadGraph").respond(httpResponseMock);

            orientDbService.loadGraph()
                .then(whenSuccess)
                .catch(whenError)
                .finally(whenFinally);

            httpBackend.flush();

        });

    });

});