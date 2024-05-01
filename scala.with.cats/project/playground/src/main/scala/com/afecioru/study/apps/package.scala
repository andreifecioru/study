package com.afecioru.study

import cats.effect.IO

package object apps {
  implicit class IODebugWrapper[T](io: IO[T]) {
    def _debug: IO[T] = for {
      value <- io
      threadName = Thread.currentThread().getName
      _ <- IO(println(s"[$threadName] > $value"))
    } yield value
  }
}
