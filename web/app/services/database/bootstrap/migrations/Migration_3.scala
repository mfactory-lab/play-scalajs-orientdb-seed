package services.database.bootstrap.migrations

import com.orientechnologies.orient.core.record.impl.ODocument
import play.Logger
import springnz.orientdb.ODBScala
import springnz.orientdb.session.ODBSession

object Migration_3 extends ODBScala {

  val createEdges: ODBSession[Unit] = ODBSession { implicit db ⇒

    Logger.debug("Running createEdges session")

    def createEdge(from: ODocument, to: ODocument, since: String): Unit = {
      val fromRid = from.getRecord.getIdentity
      val toRid = to.getRecord.getIdentity
      val q = "CREATE EDGE Knows" +
        " FROM " + fromRid +
        " TO " + toRid +
        " SET since = '" + since + "'"
      sqlCommand(q).execute()
      () //the above orient command returns null - avoid NPEs by explicitly returning Unit
    }

    val persons = selectClass("Person")(identity)

    createEdge(persons(0), persons(1), "since March 2010")
    createEdge(persons(0), persons(3), "since January 2012")
    createEdge(persons(3), persons(0), "since January 2012")
    createEdge(persons(1), persons(3), "since February 2015")
    createEdge(persons(2), persons(1), "since June 2000")

  }

}
