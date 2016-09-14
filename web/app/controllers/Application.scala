/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package controllers

import javax.inject._

import play.api._
import play.api.mvc._

@Singleton
class Application @Inject()(implicit webJarAssets: WebJarAssets,
                            environment: Environment,
                            configuration: Configuration) extends InjectedController {

  implicit val info: Map[String, String] = build.BuildInfo.toMap.map { case (k, v) => k -> v.toString }

  def index: Action[AnyContent] = Action {
    Ok(views.html.index("It works!"))
  }

}