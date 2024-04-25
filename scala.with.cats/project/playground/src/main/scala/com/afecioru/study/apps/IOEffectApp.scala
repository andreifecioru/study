package com.afecioru.study.apps

import java.util.concurrent.TimeUnit

object IOEffectApp extends App {

  case class MyIO[A](unsafe: () => A) {
    def map[B](f: A => B): MyIO[B] = {
      MyIO { () => f(unsafe()) }
    }

    def flatMap[B](f: A => MyIO[B]): MyIO[B] = {
      MyIO { () => f(unsafe()).unsafe()  }
    }
  }

  val currentTimeIO = MyIO[Long] { () => System.currentTimeMillis() }

  def measure[R](computation: MyIO[R]): MyIO[Long] = {
    for {
      startTs <- currentTimeIO
      _ <- computation
      endTs <- currentTimeIO
    } yield endTs - startTs
  }

  val computation = MyIO[Unit] { () =>
    println("Performing computation...")
    TimeUnit.SECONDS.sleep(1)
  }

  val duration = measure(computation).unsafe()
  println(s"Duration: $duration")

}
