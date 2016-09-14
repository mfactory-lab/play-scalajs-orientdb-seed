/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package controllers.api

import javax.inject.Inject

import play.api.libs.json.JsValue
import play.api.mvc.{Action, InjectedController}
import services.database.handling.DbHandler
import services.utils.Prickle._
import shared.Common._

class DatabaseController @Inject()(dbHandler: DbHandler) extends InjectedController {

  def loadGraph: Action[JsValue] = Action(parse.json) { implicit request =>
    processPickleRequestWithResponse[LoadGraphRequest.type, Graph] { _ =>
      dbHandler.getGraph
    }
  }

}