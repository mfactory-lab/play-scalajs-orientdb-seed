package services.database.bootstrap.migrations

import com.orientechnologies.orient.core.metadata.schema.OType
import com.orientechnologies.orient.core.record.impl.ODocument
import play.Logger
import springnz.orientdb.ODBScala
import springnz.orientdb.pool.ODBConnectionPool
import springnz.orientdb.session.ODBSession

object Migration_2 extends ODBScala {

  val createPersons: ODBSession[Unit] = ODBSession { implicit db ⇒

    Logger.debug("Running createPersons session")

    val vertexClass = findClass("Person")
    vertexClass.createProperty("name", OType.STRING)
    vertexClass.createProperty("age", OType.INTEGER)

    persons.foreach(
      { case (name, age) =>
        val person = new ODocument("Person")
        person.field("name", name)
        person.field("age", age)
        person.save()
      }
    )

  }

}
