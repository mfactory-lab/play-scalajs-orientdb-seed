package controllers

import play.api.mvc.InjectedController
import play.api.routing.JavaScriptReverseRoute

/**
  * Copyright 2016, Alexander Ray
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

class JsRoutesController extends InjectedController {

  lazy val routeCache: Array[JavaScriptReverseRoute] = {
    listRoutes(classOf[controllers.api.routes.javascript])
  }

  def listRoutes[T](c: Class[T]): Array[JavaScriptReverseRoute] = {
    val jsRoutesClass = c
    val controllers = jsRoutesClass.getFields.map(_.get(null))

    controllers.flatMap { controller =>
      controller.getClass.getDeclaredMethods.
        filter(_.invoke(controller).isInstanceOf[play.api.routing.JavaScriptReverseRoute]).
        map(_.invoke(controller).asInstanceOf[play.api.routing.JavaScriptReverseRoute])
    }
  }

  def jsRoutes(varName: String = "jsRoutes") = //cached(_ => "jsRoutes", duration = 86400) {

    Action { implicit request =>
      Ok(play.api.routing.JavaScriptReverseRouter(varName)(routeCache: _*)).as(JAVASCRIPT)
    }

  //  }

}
