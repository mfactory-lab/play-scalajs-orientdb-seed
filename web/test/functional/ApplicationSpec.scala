/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package functional

import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, PlaySpec}

class ApplicationSpec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {

  "Application" should {

    "work from within a browser" in {

      go to ("http://localhost:" + port)

      pageSource must include("It works!")

      eventually {
        pageSource must include("play-scalajs application template")
      }

    }

    "show graph on request" in {

      find(id("cy")).value.isDisplayed mustBe false
      find(name("loadGraphBtn")).value.isEnabled mustBe true

      click on name("loadGraphBtn")

      eventually {
        find(id("cy")).value.isDisplayed mustBe true
      }
      find(name("loadGraphBtn")).value.isEnabled mustBe false

    }

  }

}
