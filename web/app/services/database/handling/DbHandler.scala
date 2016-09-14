/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package services.database.handling

import javax.inject.Inject

import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.typesafe.config.Config
import shared.Common.{Edge, Graph, Vertex, _}
import springnz.orientdb.ODBScala
import springnz.orientdb.pool.ODBConnectionPool
import springnz.orientdb.session.ODBSession

import scala.collection.JavaConverters._
import scalaz.syntax.bind._

class DbHandler @Inject()(configuration: Config) extends ODBScala {

  def getGraph: Option[Graph] = {

    val config = configuration.getConfig(orientDbConfig)
    implicit val pool = ODBConnectionPool.fromConfig(config)

    def verticesSession() = ODBSession { implicit db =>
      val res = for {
        v <- selectClass("Person")(identity)
      } yield {
        val rid: String = v.getRecord.getIdentity.toString
        val name: String = v.getString("name")
        val age: Int = v.getInt("age")

        rid -> Vertex(rid, name, age)
      }

      res.toMap
    }

    def edgesSession(vertices: Map[String, Vertex]) = ODBSession { implicit db =>

      val dbConfig = pool.dbConfig.get
      val graph = new OrientGraph(db, dbConfig.user, dbConfig.pass, null)

      val res = graph.getEdges.asScala.map { edge =>
        Edge(
          edge.getId.toString,
          edge.getProperty("since"),
          vertices.getOrElse(edge.getVertex(Direction.OUT).getId.toString, Vertex.empty),
          vertices.getOrElse(edge.getVertex(Direction.IN).getId.toString, Vertex.empty)
        )
      }

      graph.shutdown(false)

      res.toSet

    }

    val graphSession = for {
      vertices <- verticesSession()
      edges <- edgesSession(vertices)
    } yield {
      Graph(vertices.values.toSet, edges)
    }

    graphSession.run().toOption
  }

}