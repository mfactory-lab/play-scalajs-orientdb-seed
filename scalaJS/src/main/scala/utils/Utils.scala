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

import org.scalajs.dom._
import prickle.{Pickle, Pickler, Unpickle, Unpickler}
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration.FiniteDuration
import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportAll
import scala.scalajs.js.{JSON, Thenable, |}
import scala.util.{Failure, Success, Try}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

@JSExportAll
object Utils {
  def flatten[T](future: Future[Try[T]]): Future[T] = future flatMap handleTry

  def handleTry[T](t: Try[T]): Future[T] = t match {
    case Success(s) => Future.successful(s)
    case Failure(f) => Future.failed(f)
  }

  def handleError(t: Throwable) {
    console.error(s"An error has occured: $t")
  }

  implicit class FutureCompanionObjectExtension (val f: Future.type ) extends AnyVal {

    def delayedFuture[T](millis: FiniteDuration)(block: => T): Future[T] = {
      val p = Promise[T]()

      scala.scalajs.js.timers.setTimeout(millis) {
        p.complete(Try(block))
      }

      p.future
    }
  }

  implicit class FutureObjectExtension[T: ClassTag](f: Future[T]) {

    def toPromise(recovery: Throwable => js.Any)
                 (implicit ec: ExecutionContext): scala.scalajs.js.Promise[T] = {
      new scala.scalajs.js.Promise[T] (
        (resolve: js.Function1[T | Thenable[T], _], reject: js.Function1[scala.Any, _]) =>
        {
          f.onSuccess({
            case x => resolve(x)
          })
          f.onFailure({
            case e => reject(recovery(e))
          })
        }
      )
    }

    def toDefaultPromise: js.Promise[T] = toPromise({_.getMessage})

  }

  def pickleGetRequest[R](getF: js.Dynamic)(implicit ec: ExecutionContext, u: Unpickler[R]): Future[R] = {
    val promise = getF.get().asInstanceOf[com.greencatsoft.angularjs.core.Promise[js.Dynamic]]

    Utils.flatten {
      val x = promise.map(any => JSON.stringify(any.data)).map(Unpickle[R].fromString(_, mutable.Map.empty))
      x
    }
  }

  def picklePostRequest[T, R](postF: js.Dynamic, req: T)
                             (implicit ec: ExecutionContext, u: Unpickler[R], p: Pickler[T]): Future[R] = {
    val promise: com.greencatsoft.angularjs.core.Promise[js.Dynamic] = postF.post(Pickle.intoString(req)).asInstanceOf[com.greencatsoft.angularjs.core.Promise[js.Dynamic]]

    Utils.flatten {
      val x: Future[Try[R]] = promise.map(any => JSON.stringify(any.data)).map(Unpickle[R].fromString(_, mutable.Map.empty))
      x
    }
  }



}
