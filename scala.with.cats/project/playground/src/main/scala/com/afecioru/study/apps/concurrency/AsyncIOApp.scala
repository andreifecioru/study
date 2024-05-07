package com.afecioru.study.apps.concurrency

import cats.effect.{IO, IOApp}
import com.afecioru.study.apps.IODebugWrapper

import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.ExecutionContext
import scala.util.Try

object AsyncIOApp extends IOApp.Simple {
  val threadPool = Executors.newFixedThreadPool(8)
  val ec = ExecutionContext.fromExecutorService(threadPool)

  val program1 = {

    type Callback[A] = Either[Throwable, A]

    val intIO: IO[Int] = IO.async_ { cb =>
      threadPool.execute { () =>
        val result = Try {
          TimeUnit.SECONDS.sleep(1)
          println(s"[${Thread.currentThread().getName}] Computing...")
          100
        }.toEither

        cb(result)
      }
    }

    intIO
  }

  override def run: IO[Unit] = program1._debug.void >> IO(threadPool.shutdown())
}
