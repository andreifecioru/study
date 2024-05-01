package com.afecioru.study.apps

import cats.effect.IO

import scala.util.{Failure, Success, Try}

object IOErrorHandlingApp extends App {

  def exercise_01(): Unit = {
    def option2IO[A](option: Option[A])(ifEmpty: Throwable): IO[A] = {
      option match {
        case Some(value) => IO(value)
        case None => IO.raiseError(ifEmpty)
      }
    }

    def try2IO[A](aTry: Try[A]): IO[A] = {
      aTry match {
        case Success(value) => IO(value)
        case Failure(exception) => IO.raiseError(exception)
      }
    }

    def either2IO[A](anEither: Either[Throwable, A]): IO[A] = {
      anEither match {
        case Right(value) => IO(value)
        case Left(ex) => IO.raiseError(ex)
      }
    }

    // Note: IO already has helper methods for these:
//    IO.fromOption()
//    IO.fromTry()
//    IO.fromEither()
  }

  def exercise_02(): Unit = {
    def handleIOError[A](io: IO[A])(handler: Throwable => A): IO[A] =
      io.redeem(handler, identity)

    def handleIOErrorWith[A](io: IO[A])(handler: Throwable => IO[A]) =
      io.redeemWith(handler, IO.pure)

    // NOTE: there is also IO.handleErrorWith()
  }

  // ------

  import cats.effect.unsafe.implicits.global
  IO.delay(println("Andrei"))

  IO.pure(42)

}
