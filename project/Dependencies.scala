/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin
import ScalaJSPlugin.autoImport._
import play.sbt.PlayImport

object Dependencies {

  object scalaV {
    val compiler_2_11 = "2.11.11"
    val compiler_2_12 = "2.12.3"
  }

  val resolvers = DefaultOptions.resolvers(snapshot = true) ++ Seq(
    "Restlet Repository" at "http://maven.restlet.org"
  )

  object testing {
    val scalaTest = "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.1" % Test
    val selenium = "org.seleniumhq.selenium" % "selenium-java" % "3.5.1" % Test

    val dependencies: Seq[ModuleID] = Seq(
      scalaTest,
      selenium
    )
  }

  object graphVisualisation {
    val cytoscape = "org.webjars.bower" % "cytoscape" % "3.1.1"

    val dependencies: Seq[ModuleID] = Seq(
      cytoscape
    )
  }

  object webjars {
    val webjarsPlay  = "org.webjars"         %% "webjars-play"             % "2.6.0-M1"
    val requireJs    = "org.webjars"         %  "requirejs"                % "2.3.3"
    val bootstrap    = "org.webjars"         %  "bootstrap"                % "3.3.7"      exclude("org.webjars", "jquery")
    val jquery       = "org.webjars"         %  "jquery"                   % "3.2.1"

    val angularJsVersion = "1.6.5"

    val angular = "org.webjars.bower" % "angular" % angularJsVersion exclude("org.webjars", "jquery")
    val angularMocks = "org.webjars.bower" % "angular-mocks" % angularJsVersion % Test

    val dependencies: Seq[ModuleID] = Seq(
      webjarsPlay,
      requireJs,
      angular,
      bootstrap,
      jquery,
      angularMocks
    )
  }

  object scalaJs {

    val angularVersion = "0.7"
    val prickleVersion = "1.1.14"
    val scalaJSVersion = "0.6.19"
    val stubs = "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided"
    val prickle = "com.github.benhutchison" %% "prickle" % prickleVersion

    val sharedDependencies = Def.setting(
      Seq(
        "com.greencatsoft" %%% "scalajs-angular" % angularVersion,
        "com.github.benhutchison" %%% "prickle" % prickleVersion
      )
    )

    val dependencies = Seq (
      stubs,
      prickle
    )

  }

  object sharedJs {

    val dependencies = Seq(
      scalaJs.stubs,
      scalaJs.prickle
    )

  }

  object playFramework {
    val version      = play.core.PlayVersion.current

    val jdbc         = "com.typesafe.play"   %% "play-jdbc"                % version
    val cache        = "com.typesafe.play"   %% "play-cache"               % version
    val ws           = "com.typesafe.play"   %% "play-ws"                  % version
    val json         = "com.typesafe.play"   %% "play-json"                % version
    val specs2       = "com.typesafe.play"   %% "play-specs2"              % version            % "test"

    val dependenciesOnScalaJs: Seq[ModuleID] = Seq(
      scalaJs.stubs,
      scalaJs.prickle
    )

    val dependencies: Seq[ModuleID] = Seq(
      jdbc,
      cache,
      ws,
      json,
      PlayImport.guice,
      specs2
    )
  }

  object gitDependencies {
    lazy val orientDbMigrations =
        ProjectRef(uri("git://github.com/springnz/orientdb-migrations.git"), "orientdb-migrations")
  }

  object orientDb {
    val version = "2.2.26"

    val orientDbCore        = "com.orientechnologies" % "orientdb-core" % version
    val orientDbObject      = "com.orientechnologies" % "orientdb-object" % version
    val orientDbGraphDb     = "com.orientechnologies" % "orientdb-graphdb" % version
    val orientDbServer      = "com.orientechnologies" % "orientdb-server" % version
    val orientDbEtl         = "com.orientechnologies" % "orientdb-etl" % version
    val orientDbJdbc        = "com.orientechnologies" % "orientdb-jdbc" % version
    val bluePrintsCore      = "com.tinkerpop.blueprints" % "blueprints-core" % "2.6.0"

    val clientDependencies: Seq[ModuleID] = Seq(
      orientDb.orientDbCore,
      orientDb.orientDbObject,
      orientDb.orientDbGraphDb,
      orientDb.orientDbEtl,
      orientDb.orientDbJdbc,
      orientDb.bluePrintsCore
    )

    val serverDependencies: Seq[ModuleID] = clientDependencies ++ Seq(
      orientDb.orientDbServer
    )
  }

  val webDependencies: Seq[ModuleID] =
    playFramework.dependencies ++
    playFramework.dependenciesOnScalaJs ++
    webjars.dependencies ++
    graphVisualisation.dependencies

  val commonDependencies: Seq[ModuleID] =
    testing.dependencies ++
    orientDb.clientDependencies

  val scalaJsDependencies: Seq[ModuleID] =
    scalaJs.dependencies

  val sharedJsDependencies: Seq[ModuleID] =
    sharedJs.dependencies
}
