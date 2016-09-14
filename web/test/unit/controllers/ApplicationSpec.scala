/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package unit.controllers

import controllers.Application
import org.scalatest.{FlatSpec, MustMatchers}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.FakeRequest
import play.api.test.Helpers._

class ApplicationSpec extends FlatSpec with GuiceOneAppPerSuite with MustMatchers {

  lazy val appController = app.injector.instanceOf(classOf[Application])

  "Application" should "render the index page" in {

    val index = appController.index()(FakeRequest())

    status(index) mustBe OK
    contentType(index) mustBe Some("text/html")
    contentAsString(index) must include("It works!")

  }

}