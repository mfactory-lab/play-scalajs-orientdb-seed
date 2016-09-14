/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package utils

import java.nio.file.{Files, Path, Paths}

object JavaScriptRouterGenerator {

  def generate(toPath: String): Path = {
    val jsFile = play.api.routing.JavaScriptReverseRouter("jsRoutes", None, "localhost",
      controllers.api.routes.javascript.DatabaseController.loadGraph
    ).body

    val path = Paths.get(toPath)
    Files.createDirectories(path.getParent)
    Files.write(path, jsFile.getBytes("UTF-8"))
  }

}