/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package unit.controllers.api

import akka.stream.Materializer
import controllers.api.DatabaseController
import org.scalatest.{FlatSpec, MustMatchers}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import prickle.Unpickle
import shared.Common.Graph

class DatabaseControllerSpec extends FlatSpec with GuiceOneAppPerSuite with MustMatchers {

  implicit lazy val materializer: Materializer = app.materializer
  lazy val databaseController = app.injector.instanceOf(classOf[DatabaseController])

  "DatabaseController" should "return graph" in {

    val action = databaseController.loadGraph()
    val request = FakeRequest(POST, "/api/loadGraph").withJsonBody(Json.parse("""{ "field": "value" }"""))

    // getting serialized graph
    val result = call(action, request)

    status(result) mustBe OK

    // deserializing Graph to Option
    val graphOption = Unpickle[Graph].fromString(contentAsString(result)).toOption

    graphOption mustBe a[Some[_]]

    // getting graph from Option
    val graph = graphOption.get

    graph.vertices.size mustBe 4
    graph.edges.size mustBe 5

  }

}
