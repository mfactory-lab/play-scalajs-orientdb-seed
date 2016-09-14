/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package visualize.services

import com.greencatsoft.angularjs.core.{Promise, Q}
import com.greencatsoft.angularjs.{Factory, Service, injectable}
import facades.services.PlayRoutesService
import shared.Common._
import utils.Utils._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportAll}
import js.JSConverters._

@JSExportAll
case class JSGraph(vertices: js.Array[Vertex], edges: js.Array[Edge]) extends ResponseData


@injectable("orientDbService")
class OrientDbService(playRoutesService: PlayRoutesService, implicit val q: Q) extends Service {

  @JSExport
  def loadGraph(): Promise[JSGraph] = {
    val f = picklePostRequest[LoadGraphRequest.type, Graph](
      playRoutesService.controllers.api.DatabaseController.loadGraph(), LoadGraphRequest
    ) map { graph =>
      JSGraph(graph.vertices.toJSArray, graph.edges.toJSArray)
    }

    Promise.future2promise(f)
  }

}

@injectable("orientDbService")
class OrientDbServiceFactory(playRoutesService: PlayRoutesService, q: Q) extends Factory[OrientDbService] {
  override def apply(): OrientDbService = new OrientDbService(playRoutesService, q)
}
