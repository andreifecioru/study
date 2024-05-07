package com.afecioru.study.apps.concurrency

import cats.effect.{IO, IOApp}
import com.afecioru.study.apps.IODebugWrapper

import scala.concurrent.duration._

object IOCancellationApp extends IOApp.Simple {

  val program1 = {
    // You can cancel a chain of IOs mid-way
    (IO.pure("Step 1")._debug >>
      IO.pure("Step 2")._debug >>
      IO.canceled >>                // the chain gets broken here
      IO.pure("Step 3")._debug >>   // steps below don't get executed
      IO.pure("Step 4")._debug).onCancel(IO.pure("Chain got canceled...")._debug.void)
  }

  val program2 = {
    // The `un-cancelable` mask - prevents an IO to get canceled by another fiber

    val workflow = (IO.pure("Starting workflow...")._debug >>
      IO.sleep(5.seconds) >>
      IO.pure("Workflow complete...")._debug).onCancel(IO.pure("Workflow got cancelled...")._debug.void)

    for {
      fib <- IO.uncancelable(_ => workflow).start
      // the cancellation attempt will fail
      _ <- IO.sleep(2.seconds) >> IO.pure("Attempting cancellation...")._debug >> fib.cancel
      _ <- fib.join
    } yield ()
  }

  val program3 = {
    // You can "poke holes" into the `un-cancelable` mask, effectively marking
    // sections of the workflow that can be cancelled.

    val workflow = IO.uncancelable { poll =>
      IO.pure("Starting workflow...")._debug >>
      poll(IO.pure("Step 1")._debug >> IO.sleep(3.seconds))  >> // this section is cancelable
      IO.pure("Step 2")._debug >> IO.sleep(3.seconds) >> // this section is un-cancelable
      IO.pure("Workflow complete")._debug
    }

    // if the cancellation takes place during Step 1, it will succeed, otherwise it will be ignored
    for {
      fib <- workflow.onCancel(IO.pure("Workflow got cancelled...")._debug.void).start
      _ <- IO.sleep(4.seconds) >> IO.pure("Attempting cancellation...")._debug >> fib.cancel
      _ <- fib.join
    } yield ()
  }

  val program4 = {
    val ioChain = (IO.uncancelable { poll =>
      poll(IO.pure("Step 1")._debug >> IO.sleep(1.second)) >> // cancelable region
      IO.pure("Step 2")._debug >> IO.sleep(1.second) >> // un-cancelable region
      poll(IO.pure("Step 3")._debug >> IO.sleep(1.second)) // cancelable region
    }).onCancel(IO.pure("Chain got canceled...")._debug.void)

    /*
      NOTE: even if the cancellation signal arrives in the middle of Step 2 (which is un-cancelable),
      the signal will be picked up by the first cancelable region that happens after the signal arrives
      (in this case - Step 3)
    */

    for {
      fib <- ioChain.start
      _ <- IO.sleep(1500.millis) >> IO.pure("Canceling the chain...")._debug >> fib.cancel
      _ <- fib.join
    } yield ()
  }

  override def run: IO[Unit] = {
    program4.void
  }
}
