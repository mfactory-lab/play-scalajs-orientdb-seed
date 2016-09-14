// snapshot repository
resolvers += "Sonatype OSS Snapshot Repository" at "https://oss.sonatype.org/content/repositories/snapshots/"

// updates plugin
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.1")

// Scala Style Plugin
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.9.0")

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.3")

// web plugins
addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.1")
addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.2")
addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.1")

// scala JS
addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.5")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.19")

// To get build info in the app
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")

// Heroku
addSbtPlugin("com.heroku" % "sbt-heroku" % "1.0.0")