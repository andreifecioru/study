package com.afecioru.study.apps

import cats.effect.{IO, IOApp}

import java.util.concurrent.TimeUnit
import scala.util.Random

object IOParallelismApp extends IOApp.Simple{

  private def step(idx: Int): IO[Int] = IO {
    TimeUnit.MILLISECONDS.sleep(Random.nextInt(1000))
    idx
  }._debug

  private val program1 = (step(1) >> step(2) >> step(3)).void

  private val program2 = {
    import cats.Parallel
    import cats.effect.implicits._
    import cats.implicits._

    // move to parallel world
    val step1 = Parallel[IO].parallel(step(1))
    val step2 = Parallel[IO].parallel(step(2))
    val step3 = Parallel[IO].parallel(step(3))
    val step4 = Parallel[IO].parallel(step(4))
    val step5 = Parallel[IO].parallel(step(5))

    val inParallel = (step1, step2, step3, step4, step5)
      .mapN{ (r1, r2, r3, r4, r5) => r1 + r2 + r3 + r4 + r5 }

    // switch back to sequential world to be able to use the result
    Parallel[IO].sequential(inParallel)._debug.void
  }

  private val program3 = {
    import cats.effect.implicits._
    import cats.implicits._

    val step1 = step(1)
    val step2 = step(2)
    val step3 = step(3)
    val step4 = step(4)
    val step5 = step(5)

    // parMapN handles this back-and-forth between parallel/sequential automatically
    (step1, step2, step3, step4, step5).parMapN { (r1, r2, r3, r4, r5) =>
      r1 + r2 + r3 + r4 + r5
    }._debug.void
  }

  override def run: IO[Unit] = {
    program3
  }
}
