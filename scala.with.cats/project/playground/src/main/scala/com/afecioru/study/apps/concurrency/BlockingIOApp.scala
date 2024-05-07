package com.afecioru.study.apps.concurrency

import cats.effect.{IO, IOApp}
import com.afecioru.study.apps.IODebugWrapper

import java.util.concurrent.{ExecutorService, Executors, TimeUnit}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object BlockingIOApp extends IOApp.Simple {

  val program1 = {
    // a seq if IOs is run on a special thread pool (called the compute pool)
    IO.pure("Step 1")._debug >> IO.sleep(500.millis) >>
    IO.pure("Step 2")._debug >> IO.sleep(500.millis) >>
    IO.pure("Step 3")._debug >> IO.sleep(500.millis) >>
    IO.pure("Done...")._debug

    /* SEMANTIC BLOCKING:
      When IO.sleep() is invoked, no actual thread is being blocked. The current thread is being released
      (i.e. control is yielded). A new `task` is scheduled to run after 500 millis (possibly on another
      thread in the pool)
    */
  }

  val program2 = {
    // Blocking IOs are executed on a special thread pool (called the blocking pool)

    IO.blocking {
      println(s"[${Thread.currentThread().getName}] Computing...")
      TimeUnit.SECONDS.sleep(4)
      100
    }._debug
  }

  def program3: IO[Int] = {
    // You can manually yield control of the thread (via the IO.cede API).
    // Subsequent IOs are (possibly - but not likely) executed on a different thread in the pool

    // NOTE: you can run the IOs on a self-managed thread-pool (use `.evalOn(ec))
    val ec = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
    (1 to 1000).map(IO.pure).reduce(_._debug >> IO.cede >> _._debug).evalOn(ec)
  }

  override def run: IO[Unit] = program3.void
}
