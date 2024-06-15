package com.afecioru.study.apps.coordination

import scala.concurrent.duration._
import cats.effect.kernel.Ref
import cats.effect.{IO, IOApp}
import com.afecioru.study.apps.IODebugWrapper

object Refs extends IOApp.Simple {

  // Refs - functional implementation of atomic references

  // definition
  private val aValue: IO[Ref[IO, Int]] = Ref[IO].of(42)
  private val aValue_v2 = IO.ref(42)

  // setting the value in a Ref (itself an effect)
  aValue.flatMap(_.set(10)) // IO[Unit]

  // getting the value
  aValue.flatMap(_.get) // IO[Int]

  // get & set (returns the old value)
  aValue.flatMap(_.getAndSet(10)) // IO[Int]

  // modification via a function
  aValue.flatMap(_.update(_ + 1)) // IO[Unit]
  aValue.flatMap(_.updateAndGet(_ + 1)) // IO[Int] - with the new value
  aValue.flatMap(_.getAndUpdate(_ + 1)) // IO[Int] - with the old value

  // modification, but returning a different type
  aValue.flatMap(_.modify(v => {
    // return a tuple with the new value in the ref and the return value (of maybe a different type)
    v + 1 -> s"Current value: $v"
  })) // IO[String]

  // main use-case: concurrent & tread-safe reads/writes over shared values (functional way)
  private def wordCount(input: String): Int = input.split(" ").length

  private val inputs = Seq(
    "one two three",
    "one two three four",
    "one two three four five"
  )

  private val program1 = {
    val aRef = IO.ref(0)

    import cats.syntax.parallel._

    def totalWordCount(ref: Ref[IO, Int]): IO[Seq[Unit]] = inputs.map { input =>
      ref.update(_ + wordCount(input))
    }.parSequence

    for {
      ref <- aRef
      _ <- totalWordCount(ref)
      result <- ref.get
    } yield result
  }

  private val program2 = {
    var ticks = 0L

    def oneSecondClock: IO[Unit] = for {
      _ <- IO.sleep(1.second)
      _ <- IO.pure(s"Current time: ${System.currentTimeMillis()}")._debug
      _ <- IO(ticks += 1)
      _ <- oneSecondClock
    } yield ()

    def fiveSecondClock: IO[Unit] = for {
      _ <- IO.sleep(5.seconds)
      _ <- IO.pure(s"Tick count: $ticks")._debug
      _ <- fiveSecondClock
    } yield ()

    import cats.syntax.parallel._
    (oneSecondClock, fiveSecondClock).parTupled
  }

  private val program3 = {
    def oneSecondClock(ref: Ref[IO, Long]): IO[Unit] = for {
      _ <- IO.sleep(1.second)
      _ <- IO.pure(s"Current time: ${System.currentTimeMillis()}")._debug
      _ <- ref.update(_ + 1)
      _ <- oneSecondClock(ref)
    } yield ()

    def fiveSecondClock(ref: Ref[IO, Long]): IO[Unit] = for {
      _ <- IO.sleep(5.seconds)
      tickCount <- ref.get
      _ <- IO.pure(s"Tick count: $tickCount")._debug
      _ <- fiveSecondClock(ref)
    } yield ()

    import cats.syntax.parallel._
    for {
      ref <- IO.ref(0L)
      _ <- (oneSecondClock(ref), fiveSecondClock(ref)).parTupled
    } yield ()
  }

  override def run: IO[Unit] =
    program3._debug.void
}
