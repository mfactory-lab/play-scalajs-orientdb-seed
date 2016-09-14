package services.database.bootstrap.migrations

import play.Logger
import springnz.orientdb.ODBScala
import springnz.orientdb.session.ODBSession

object Migration_1 extends ODBScala {

  val createClasses: ODBSession[Unit] = ODBSession { implicit db ⇒

    Logger.debug("Running createClasses session")

    // Creating Vertex Class
    sqlCommand("CREATE CLASS Person EXTENDS V").execute()

    // Creating Edge Class
    sqlCommand("CREATE CLASS Knows EXTENDS E").execute()
    () //the above orient command returns null - avoid NPEs by explicitly returning Unit

  }

}
