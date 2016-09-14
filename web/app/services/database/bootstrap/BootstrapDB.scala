/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package services.database.bootstrap

import javax.inject.Inject

import com.typesafe.config.Config
import services.database.bootstrap.migrations._
import springnz.orientdb.ODBScala
import springnz.orientdb.migration.{Migration, Migrator, ODBMigrations}
import springnz.orientdb.pool.ODBConnectionPool

import scala.util.Try

class BootstrapDB @Inject()(configuration: Config) extends ODBMigrations with ODBScala {

  val orientDbConfig = "orientDB"

  def run(): Try[Unit] = {
    Migrator.runMigration(migrations)
  }

  implicit val pool = ODBConnectionPool.fromConfig(configuration.getConfig(orientDbConfig))

  override def migrations: Seq[Migration] = {
    List(
      Migration(1, Migration_1.createClasses),
      Migration(2, Migration_2.createPersons),
      Migration(3, Migration_3.createEdges)
    )
  }

}
