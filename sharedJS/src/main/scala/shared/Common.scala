/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package shared

import scala.scalajs.js.annotation.JSExportAll
import scala.util.Try

object Common {

  val orientDbConfig = "orientDB"

  trait Request
  trait ResponseData

  case class Response(data: Try[ResponseData])


  case object LoadGraphRequest extends Request

  @JSExportAll
  case class Vertex(rid: String, name: String, age: Int)

  object Vertex {
    def empty = Vertex("#0:0", "", 0)
  }

  @JSExportAll
  case class Edge(rid: String, since: String, out: Vertex, in: Vertex)

  @JSExportAll
  case class Graph(vertices: Set[Vertex], edges: Set[Edge]) extends ResponseData
}