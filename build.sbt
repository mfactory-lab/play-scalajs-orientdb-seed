import CommonSettings._
import Dependencies._
import sbt.Keys._
import sbt.Project.projectToRef

scalaVersion := scalaV.compiler_2_12

name := projectName

publishMavenStyle := false

lazy val web = (
    PlayProject("web")
    settings(
      libraryDependencies ++= webDependencies ++
        commonDependencies
    )
    settings(
        publishMavenStyle := false,
        routesGenerator := InjectedRoutesGenerator,
        scalaJSProjects := jsProjects,
        pipelineStages in Assets := Seq(scalaJSPipeline),
        pipelineStages := Seq(digest, gzip),
        buildInfoPackage := "build",
        buildInfoOptions += BuildInfoOption.ToMap,
        buildInfoKeys ++= Seq (
          "projectName" -> projectName
        ),
        herokuAppName in Compile := "play-scalajs-orientdb-seed"
    )
  ).
  dependsOn (sharedJS_JVM, Dependencies.gitDependencies.orientDbMigrations)


lazy val scalaJS = (
    ScalaJsProject("scalaJS")
    settings(
      libraryDependencies ++=
        scalaJs.sharedDependencies.value ++
        scalaJsDependencies
    )
    settings(
        skip in packageJSDependencies := true,
        scalaJSStage in Global := FastOptStage,
        herokuAppName in Compile := "play-scalajs-orientdb-seed"
    )
  ) dependsOn sharedJS_JS

lazy val jsProjects = Seq(scalaJS)

lazy val sharedJS = (
    SharedJsProject("sharedJS")
    settings(
      libraryDependencies ++= sharedJsDependencies
    )
  )

lazy val sharedJS_JS = sharedJS.js
lazy val sharedJS_JVM = sharedJS.jvm

fork in run := true

// loads the Play project at sbt startup
onLoad in Global := (Command.process("project web", _: State)) compose (onLoad in Global).value