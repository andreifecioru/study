package com.afecioru.study.apps.concurrency

import cats.effect.kernel.Outcome.{Errored, Succeeded, Canceled}
import cats.effect.{Fiber, IO, IOApp}
import com.afecioru.study.apps.IODebugWrapper

import java.util.concurrent.TimeUnit
import scala.concurrent.duration._

object FibersBasicsApp extends IOApp.Simple {

  private val program1 = {
    val io1 = IO { TimeUnit.SECONDS.sleep(3); "Andrei" }._debug
    val io2 = IO.pure("Hello, ")._debug

    val aFiber: IO[Fiber[IO, Throwable, String]] = io1.start

    for {
      greet <- io2
      fib <- aFiber
      name <- fib.join // returns Outcome[IO, Throwable, A]
    } yield greet + name

    /**
     NOTE: fib.join is an effect that waits for the fiber to complete on another thread
     Possible outcomes:
     - success (with an IO[A])
     - failure (with an exception)
     - cancelled
     */
  }

  private val program2 = {
    val task = {
      IO.pure("Starting")._debug >>
        IO.sleep(5.seconds) >>
        IO.pure("Done...")._debug
    }.onCancel { // cancellation handler > allows for resource de-allocation & cleanup ops
      IO.pure("Cancelled... cleaning up")._debug.void
    }

    for {
      fib <- task.start
      _ <- IO.sleep(2.seconds) >> IO.pure("Cancelling task")._debug
      _ <- fib.cancel
      result <- fib.join
    } yield result
  }

  private val exercise_01 = {
    /*
      Write a function that runs an IO on another thread and (depending on the result):
        - return the result in an IO
        - if error/cancelled, return a failed IO
    */
    def runIO[A](io: IO[A]): IO[A] = {
      (for {
        fib <- io.start
        // _ <- IO.sleep(1.seconds) >> fib.cancel
        result <- fib.join
      } yield {
        result match {
          case Succeeded(effect) => effect
          case Errored(error) => IO.raiseError(error)
          case Canceled() => IO.raiseError(new RuntimeException("cancelled"))
        }
      }).flatten
    }

    // runIO(IO.pure("Starting...")._debug >> IO.sleep(3.seconds) >> IO.pure("Done..."))
    runIO(IO.pure("Starting...")._debug >> IO.sleep(3.seconds) >> IO.raiseError(new RuntimeException("Failed...")))
  }

  private val exercise_02 = {
    /*
      Write a function that takes two IOs, runs them on 2 different threads (i.e. fibers) and returns
      an IO with a tuple of the 2 results:
        - if both IOs complete successfully, tuple their results
        - if 1st IO fails, raise the error and ignore the 2nd IO result
        - if 2nd IO fails, raise the error
        - if any gets cancelled, raise an error
    */
    def runIOs[A, B](ioA: IO[A], ioB: IO[B]): IO[(A, B)] = {
      (for {
        fibA <- ioA.start
        fibB <- ioB.start
        // _ <- IO.sleep(2.seconds) >> IO.pure("Cancelling task B")._debug >> fibB.cancel
        resultA <- fibA.join
        resultB <- fibB.join
      } yield {
        (resultA, resultB) match {
          case Succeeded(effA) -> Succeeded(effB) => effA.flatMap(a => effB.map(b => (a, b)))
          case Errored(errA) -> _ => IO.raiseError(errA)
          case Succeeded(_) -> Errored(errB) => IO.raiseError(errB)
          case Canceled() -> _ | _ -> Canceled() => IO.raiseError(new RuntimeException("Cancelled"))
        }
      }).flatten
    }

    val taskA = IO.pure("Starting task A")._debug >>
      IO.sleep(3.seconds) >>
      //  IO.raiseError(new RuntimeException("Task A failed")) >>
      IO.pure("Task A completed")._debug

    val taskB = IO.pure("Starting task B")._debug >>
      IO.sleep(5.seconds) >>
      IO.pure("Task B completed")._debug

    runIOs(taskA, taskB)
  }

  val exercise_03 = {
    /*
      Write a function that adds a timeout to an IO:
        - IO runs on a fiber
        - if the timeout expires, cancel the fiber
        - return value:
          - the original value if computation successful before timeout expires
          - the exception if the computation fails before timeout expires
          - a runtime exception if the timeout expires before computation completes
    */
    def addTimeout[A](io: IO[A], timeout: FiniteDuration): IO[A] = {
      (for {
        fib <- io.start
        // careful - fibers can leak (make sure that resources are de-allocated)
        _ <- { IO.sleep(timeout) >> IO.pure("Cancelling computation")._debug >> fib.cancel }.start
        result <- fib.join
      } yield {
        result match {
          case Succeeded(effect) => effect
          case Errored(error) => IO.raiseError(error)
          case Canceled() => IO.raiseError(new RuntimeException("Computation was cancelled"))
        }
      }).flatten
    }

    val computation = IO.pure("Start computation")._debug >>
      IO.sleep(3.seconds) >>
      // IO.raiseError(new RuntimeException("Computation failed"))
      IO.pure("Computation complete")._debug

    addTimeout(computation, 5.seconds)
  }


  override def run: IO[Unit] = {
    exercise_03._debug.void
  }
}
