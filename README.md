# Play-scalajs-orientdb multiproject seed

This is an activator project which provides a seed to start working with OrientDB, Play & ScalaJS using AngularJS.

## Quick Overview

This is a multiproject build that shows how to use [Play2](http://www.playframework.com/),
[ScalaJS](https://www.scala-js.org/),
[OrientDB](http://orientdb.com/),
[RequireJS](http://requirejs.org/) and
[AngularJS](https://angularjs.org/) in order to create a simple single-page application that displays data stored
in a database.

[Here](http://play-scalajs-orientdb-seed.alexray.me/) you can find the working example of this seed.

Server side of the application is based on Play2 Framework. Play is a high-velocity web framework for Java and Scala.
It is based on a lightweight, stateless, web-friendly architecture, and follows MVC patterns and principles.
Client side uses AngularJS framework and is written in ScalaJS and native Javascript.

## Interaction with OrientDB

OrientDB is a Multi-Model Open Source NoSQL DBMS that combines the power of graphs and the flexibility of documents
into one scalable, high-performance operational database. Database interaction tools provided by the
[orientdb-migrations library](https://github.com/springnz/orientdb-migrations) enables interacting with the database
in an asynchronous reactive way.

At first step in _OnApplicationStart_ point, located in _web_ module, we create several test records in the database
which represents a simple graph with a few nodes and edges between them. Data stucture of the graph is defined in
_SharedJS_ module

## Communication between back-end and front-end

To transit data from database to front-end application uses [Prickle](https://github.com/benhutchison/prickle) library
that allows easily pickling (serializing) object graphs between Scala and Scala.js.

When the user, at client side, requests data from the database, the application starts an _OrientDbService_ of the
_ScalaJS_ module to deserialize data provided and serialized by the server-side _DatabaseController.loadGraph()_ method.
 _DatabaseController_ uses [orientdb-migrations](https://github.com/springnz/orientdb-migrations) library to load data
 from OrientDB.

## Client side

Client side of application is based on AngularJS and Twitter Bootstrap framework. It allows creating controllers both
in ScalaJS and native Javascript. In order to visualise a graph represented in Scala classes, the application uses
[Cytoscape.js](http://js.cytoscape.org/) library

## Testing

Application has four levels of testing:
*   Unit tests of back-end modules is written with ScalaTest and located in _web/test/unit_ package.
*   Unit tests of front-end AngularJS-driven modules is performed by
    [Karma](http://karma-runner.github.io/1.0/index.html). They are located in _web/test/midway_ package. 
*   Functional tests is to check functionality of the whole system and located in _web/test/functional_ package.
    Tests is written with ScalaTest and runs in headless browser.
*   End-to-end tests is to check the behavior of application running in a real browser, interacting with it as a user
    would. Tests located in _web/test/e2e_ package.
    This tests is performed by [Protractor framework](http://www.protractortest.org)
    
    To run tests first you need install Karma and Protractor modules:
    
    ```$ npm install```
    
    Then type
    
    ```$ sbt test```

## Copyright

Copyright © 2016, Alexander Ray

Developed by: Alexander Ray (dev@alexray.me), Maksim Budilovskiy (maksim.budilovskiy@gmail.com)

## License

Licensed under the Apache License, Version 2.0