/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package unit.models

import org.scalatest.{FlatSpec, MustMatchers}
import shared.Common.{Edge, Graph, Vertex}

class CommonSpec extends FlatSpec with MustMatchers {

  val rid1 = "1"
  val name1 = "Name1"
  val age1 = 21

  val rid2 = "2"
  val name2 = "Name2"
  val age2 = 22

  "Vertex" should "have correct fields" in {

    val vertex = Vertex(rid1, name1, age1)

    vertex.rid mustBe rid1
    vertex.name mustBe name1
    vertex.age mustBe age1

  }

  "Edge" should "have correct fields" in {

    val rid = "3"
    val since = "since 0"

    val vertex1 = Vertex(rid1, name1, age1)
    val vertex2 = Vertex(rid2, name2, age2)

    val edge = Edge(rid, since, vertex1, vertex2)

    edge.rid mustBe rid
    edge.since mustBe since
    edge.out mustBe vertex1
    edge.in mustBe vertex2

  }

  "Graph" should "have correct fields" in {

    val rid = "3"
    val since = "since 0"

    val vertex1 = Vertex(rid1, name1, age1)
    val vertex2 = Vertex(rid2, name2, age2)

    val edge = Edge(rid, since, vertex1, vertex2)

    val graph = Graph(Set(vertex1, vertex2), Set(edge))

    graph.vertices.size mustBe 2
    graph.vertices.foreach { vertex =>
      vertex mustBe a [Vertex]
    }

    graph.edges.size mustBe 1
    graph.edges.foreach { edge =>
      edge mustBe an [Edge]
    }

  }

}
