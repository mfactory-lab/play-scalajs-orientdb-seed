/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.cross.CrossProject
import play.sbt.PlayScala
import sbt.Keys._
import sbt._
import sbtbuildinfo.BuildInfoPlugin
import webscalajs.ScalaJSWeb

object CommonSettings {

  val javacOptionsS = Seq(
    "-source", "1.8",
    "-target", "1.8"
  )

  // Scala Compiler Options
  val scalacOptionsS = Seq(
    "-target:jvm-1.8",
    "-encoding", "UTF-8",
    "-deprecation", // warning and location for usages of deprecated APIs
    "-feature", // warning and location for usages of features that should be imported explicitly
    "-unchecked", // additional warnings where generated code depends on assumptions
    "-Xlint", // recommended additional warnings
    "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
    "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
    "-Ywarn-inaccessible",
    "-Ywarn-dead-code",
    "-Xlog-implicits"
  )

  val projectName = "play-scalajs-orientdb-seed"


  val projectSettings = Seq(
    organization := "me.alexray",
    version := "0.0.2",
    scalaVersion := Dependencies.scalaV.compiler_2_11,
    crossScalaVersions := Seq(scalaVersion.value, Dependencies.scalaV.compiler_2_12),
    resolvers ++= Dependencies.resolvers,
//    fork in Test := true, disabled because of scalaJS
    parallelExecution in Test := true,
    crossPaths := false
  )

  def PlayProject(moduleName: String): Project = (
    BaseProject(moduleName)
      enablePlugins(PlayScala, BuildInfoPlugin)
    )

  def BaseProject(moduleName: String): Project = (
    Project(projectName(moduleName), file(moduleName))
      settings (projectSettings: _*)
    )

  def ScalaJsProject(moduleName: String): Project = (
    BaseProject(moduleName)
      enablePlugins(ScalaJSPlugin, ScalaJSWeb)
    )

  def SharedJsProject(moduleName: String): CrossProject = (
    CrossProject(projectName(moduleName), file(moduleName), org.scalajs.sbtplugin.cross.CrossType.Pure)
      settings (projectSettings: _*)
      jsConfigure (_ enablePlugins ScalaJSWeb)
    )

  def projectName(moduleName: String): String = moduleName
}
