package com.afecioru.study.apps

import cats.effect.{IO, IOApp}
import jdk.nashorn.internal.ir.LiteralNode

import java.util.concurrent.TimeUnit
import scala.util.Random

object IOTraverseApp extends IOApp.Simple {

  private def step(idx: Int): IO[Int] = IO {
    TimeUnit.MILLISECONDS.sleep(Random.nextInt(1000))
    idx
  }._debug

  private val program1 = IO {
    val nums = (1 to 10).toList

    def toOption(num: Int): Option[Int] = num match {
      case _ if num % 3 == 0 => Some(num + 10)
      case _ => Some(num)
    }

    import cats.Traverse
    import cats.implicits._

    val result = Traverse[List].traverse(nums)(toOption)
    println(result)
  }.void

  val program2 = {
    val nums = (1 to 10).toList

    import cats.Traverse

    Traverse[List].traverse(nums)(step)._debug.void
  }

  val program3 = {
    val nums = (1 to 10).toList

    import cats.syntax.parallel._
    nums.parTraverse(step)._debug.void
  }

  val exercise_01 = {
    import cats.Traverse

    def sequence[A](ios: List[IO[A]]): IO[List[A]] = {
      import cats.implicits._
      Traverse[List].traverse(ios)(identity)
    }

    def sequence_v2[F[_] : Traverse, A](ios: F[IO[A]]): IO[F[A]] = {
      Traverse[F].traverse(ios)(identity)
    }
  }

  override def run: IO[Unit] = {
    program3
  }
}
