/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package e2e

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

import scala.io.Source._
import scala.sys.process._

class E2ESpec extends PlaySpec with GuiceOneServerPerSuite {

  "Application" should {

    "pass protractor tests" in {
      runProtractorTests must equal(0)
    }
  }

  private def runProtractorTests: Int = {

    val isWindows = System.getProperty("os.name").startsWith("Windows")
    val fileName = if (isWindows) "protractor.cmd" else "protractor"

    val process = s"../node_modules/.bin/$fileName test/e2e/conf.js"

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