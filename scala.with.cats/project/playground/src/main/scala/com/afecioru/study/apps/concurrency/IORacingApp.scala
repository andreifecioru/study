package com.afecioru.study.apps.concurrency

import cats.effect.kernel.Outcome.{Canceled, Errored, Succeeded}
import cats.effect.{Fiber, IO, IOApp, Outcome}
import com.afecioru.study.apps.IODebugWrapper

import scala.concurrent.duration._

object IORacingApp extends IOApp.Simple {

  val intIO = (IO.sleep(1.second) >> IO.pure(100))
    .onCancel(IO.pure("Int IO got canceled.")._debug.void)

  val strIO = (IO.sleep(2.seconds) >> IO.pure("A simple string"))
    .onCancel(IO("String IO got canceled.")._debug.void)

  val program1 = {
    /**
     With IO.race() you can launch 2 IOs. The IO that completes 1st wins; the loser gets canceled.

     The return type is a `IO[Either[A, B]]` where A and B are the types of the two input IOs.
     */

    val raceIO: IO[Either[Int, String]] = IO.race(intIO, strIO)

    raceIO.flatMap {
      case Left(intValue) => IO.pure(s"Got back an integer value: $intValue")._debug
      case Right(strValue) => IO.pure(s"Got back a string value: $strValue")._debug
    }
  }

  val program2 = {
    /**
     The `IO.racePair()` allows you greater control of the IO that "loses".

     The return type is an `IO[Either[(...),(...)]` (IO of Either of two tuples):
      - 1st tuple -> the outcome of the 1st IO (the winner) and the fiber of the 2nd IO (the loser)
      - 2nd tuple -> the fiber of the 1st IO (the loser) and the outcome of the 2nd IO (the winner)
    */

    val raceIO: IO[Either[
      (Outcome[IO, Throwable, Int], Fiber[IO, Throwable, String]),
      (Fiber[IO, Throwable, Int], Outcome[IO, Throwable, String])
    ]] = IO.racePair(intIO, strIO)

    raceIO.flatMap {
      case Left(resultInt -> fibStr) =>
        IO.pure(s"The intIO won: $resultInt")._debug >> fibStr.cancel

      case Right(fibInt -> resultStr) =>
        IO.pure(s"The strIO won: $resultStr")._debug >> fibInt.cancel
    }
  }

  val exercise_01 = {
    // Implement an IO with timeout (using race)

    // NOTE: there is an API available: anIO.timeout(5.seconds)

    def ioWithTimeout[A](io: IO[A], timeout: FiniteDuration): IO[A] = {
      val timeoutIO = IO.sleep(timeout).onCancel(IO.pure("Computation finished in time...")._debug.void)

      IO.race(io, timeoutIO).flatMap {
        case Left(value) => IO(value)
        case Right(_) => IO.raiseError(new RuntimeException("Computation timed out..."))
      }
    }

    val computation = IO.pure("Computing...")._debug >> IO.sleep(2.seconds)
    ioWithTimeout(computation, 3.seconds)

    // same as: computation.timeout(3.seconds)
  }

  val exercise_02 = {
    // implement a racing IO where the losing effect is returned

    def unrace[A, B](ioA: IO[A], ioB: IO[B]): IO[Either[A, B]] = {
      IO.racePair(ioA, ioB).flatMap {
        case Left(_ -> fibB) => fibB.join.flatMap {
          case Succeeded(valB) => valB.map(Right(_))
          case Errored(ex) => IO.raiseError(ex)
          case Canceled() => IO.raiseError(new RuntimeException("IO got canceled..."))
        }
        case Right(fibA -> _) => fibA.join.flatMap {
          case Succeeded(valA) => valA.map(Left(_))
          case Errored(ex) => IO.raiseError(ex)
          case Canceled() => IO.raiseError(new RuntimeException("IO got canceled..."))
        }
      }
    }

    unrace(intIO, strIO).flatMap {
      case Left(intValue) => IO.pure(s"The int IO lost: $intValue")._debug
      case Right(strValue) => IO.pure(s"The str IO lost: $strValue")._debug
    }
  }

  val exercise_03 = {
    // implement race using the racePair API

    def simpleRace[A, B](ioA: IO[A], ioB: IO[B]): IO[Either[A, B]] = {
      IO.racePair(ioA, ioB).flatMap {
        case Left(resA -> fibB) => resA match {
          case Succeeded(valA) => fibB.cancel >> valA.map(Left(_))
          case Errored(ex) => fibB.cancel >> IO.raiseError(ex)
          case Canceled() => fibB.join.flatMap {
            case Succeeded(valB) => valB.map(Right(_))
            case Errored(ex) => IO.raiseError(ex)
            case Canceled() => IO.raiseError(new RuntimeException("Cancelled..."))
          }
        }

        case Right(fibA -> resB) => resB match {
          case Succeeded(valB) => fibA.cancel >> valB.map(Right(_))
          case Errored(ex) => fibA.cancel >> IO.raiseError(ex)
          case Canceled() => fibA.join.flatMap {
            case Succeeded(valA) => valA.map(Left(_))
            case Errored(ex) => IO.raiseError(ex)
            case Canceled() => IO.raiseError(new RuntimeException("Cancelled..."))
          }
        }
      }
    }

    simpleRace(intIO, strIO).flatMap {
      case Left(intValue) => IO.pure(s"The int IO won: $intValue")._debug
      case Right(strValue) => IO.pure(s"The str IO won: $strValue")._debug
    }
  }

  override def run: IO[Unit] = {
    exercise_03.void
  }
}
