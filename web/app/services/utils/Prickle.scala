/**
  * Copyright 2016, Alexander Ray (dev@alexray.me)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  **/

package services.utils

import play.api.libs.json.{JsObject, JsValue}
import play.api.mvc.Results._
import play.api.mvc.{Result, _}
import prickle._
import shared.Extensions._

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

object Prickle {

  def processPickleRequestWithResponse[T, V](process: T => Option[V])(implicit u: Unpickler[T], p: Pickler[V], request: Request[JsValue]): Result = {
    val data = request.body.as[JsObject]

    Unpickle[T].fromString(data.toString, mutable.Map.empty).map { req =>
      process(req).map(res => Ok(Pickle.intoString[V](res, PickleState()))).getOrElse(NotFound)
    } recover {
      case e => BadRequest(s"Failed to parse request : $e")
    } getOrElse BadRequest
  }

  def processPickleRequestWithResponseAsync[T, V](process: T => Future[Option[V]])(implicit u: Unpickler[T],
                                                                                   p: Pickler[V],
                                                                                   request: Request[JsValue],
                                                                                   executionContext: ExecutionContext): Future[Result] = {
    val data = request.body.as[JsObject]

    val f =
      for {
        unPickled <- Unpickle[T].fromString(data.toString, mutable.Map.empty).toFuture
        optResult <- process(unPickled)
      } yield optResult.fold[Result](NotFound)(res => Ok(Pickle.intoString[V](res, PickleState())))

    f.recover {
      case e => BadRequest(s"Failed to parse request : $e")
    }
  }


  def processPickleRequest[T](process: T => Unit)(implicit u: Unpickler[T], request: Request[JsValue]): Result = {
    val data = request.body.as[JsObject]

    Unpickle[T].fromString(data.toString, mutable.Map.empty).map { req =>
      process(req)
      Ok
    } recover {
      case e => BadRequest(s"Failed to parse request : $e")
    } getOrElse BadRequest
  }


}
