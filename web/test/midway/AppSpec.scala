/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package midway

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import utils.JavaScriptRouterGenerator

import scala.io.Source._
import scala.sys.process._

class AppSpec extends PlaySpec with GuiceOneServerPerSuite {

  "Application" should {

    "pass tests with Karma" in {
      // generating jsRoutes
      JavaScriptRouterGenerator.generate("target/web/jsrouter/jsRoutes.js")
      // run tests and wait for result
      runKarmaTests must equal(0)
    }

  }

  private def runKarmaTests: Int = {
    val isWindows = System.getProperty("os.name").startsWith("Windows")
    val fileName = if (isWindows) "karma.cmd" else "karma"

    val process = s"../node_modules/.bin/$fileName start test/midway/karma-midway.conf.js"

    process
      .run(getProcessIO)
      .exitValue()
  }

  private def getProcessIO: ProcessIO = {
    new ProcessIO(_ => (),
      stdout => fromInputStream(stdout).getLines().foreach(println),
      _ => ())
  }

}
