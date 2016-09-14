/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

import com.greencatsoft.angularjs.Angular
import visualize.controllers.VisualizeGraphController
import visualize.services.OrientDbServiceFactory

import scala.scalajs.js
import scala.scalajs.js.JSApp

object App extends JSApp {

  override def main(): Unit = {

    val module = Angular.module("test-module.scalajs-app",
      Seq(
        "test-module.common.services.playRoutesService"
      )
    )

    module.
      controller[VisualizeGraphController].
      factory[OrientDbServiceFactory]

    js.Dynamic.global.console.log("Scala-App-Module - initialized")

  }

}
