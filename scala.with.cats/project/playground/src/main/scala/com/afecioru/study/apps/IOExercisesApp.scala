package com.afecioru.study.apps

import cats.effect.IO


object IOExercisesApp extends App {

  def exercise_00(): Unit = {
    import cats.effect.unsafe.implicits.global
    val someIO = IO { println("Andrei") }

    someIO.unsafeRunSync()
  }

  def exercise_01(): Unit = {
    import cats.effect.unsafe.implicits.global

    def seqTakeLast[A, B](ioa: IO[A], iob: IO[B]): IO[B] = {
      for {
        _ <- ioa
        b <- iob
      } yield b
    }

    // ioa *> iob  // andThen operator
    // ioa >> iob  // andThen operator (passed by name)

    seqTakeLast(
        IO { println("IO[A]"); 50 },
        IO { println("IO[B]"); "Andrei" }
      )
      .map(println)
      .unsafeRunSync()

  }

  def exercise_02(): Unit = {
    import cats.effect.unsafe.implicits.global

    def seqTakeFirst[A, B](ioa: IO[A], iob: IO[B]): IO[A] = {
      for {
        a <- ioa
        _ <- iob
      } yield a
    }

    // ioa <* iob // andThen & take 1st operator

    seqTakeFirst(
        IO { println("IO[A]"); 50 },
        IO { println("IO[B]"); "Andrei" }
      )
      .map(println)
      .unsafeRunSync()

  }

  def exercise_03(): Unit = {
    import cats.effect.unsafe.implicits.global

    def forever[A](io: IO[A]): IO[A] = {
      io.flatMap(_ => forever(io))
    }

    // io >> forever(io)
    // io.foreverM

    forever( IO { println("Infinite loop") } )
      .unsafeRunSync()
  }

  def exercise_04(): Unit = {
    import cats.effect.unsafe.implicits.global

    def convert[A, B](ioa: IO[A], value: B): IO[B] = {
      ioa.map(_ => value)
    }

    // ioa.as(value)

    def asUnit[A](ioa: IO[A]): IO[Unit] = convert(ioa, ())

    // ioa.void

    convert(
      IO { println("IO[A]"); "Andrei" },
      100
    )
      .map(println)
      .unsafeRunSync()

    asUnit(IO { println("IO[A]"); "Andrei" } )
      .map(println)
      .unsafeRunSync()
  }

  def exercise_05(): Unit = {
    import cats.effect.unsafe.implicits.global

    def sum(n: Int): IO[Int] = {
      def _sum(n: IO[(Int, Int)]): IO[(Int, Int)] = {
        n.flatMap { v =>
          val (num, acc) = v
          if (num == 0) IO.pure(v)
          else _sum(IO.pure(num - 1, acc  + num))
        }
      }

      _sum(IO.pure(n -> 0)).map(_._2)
    }

    println(sum(20000).unsafeRunSync())

    def sum_v2(n: Int): IO[Int] = {
      if (n <= 0) IO(0)
      else {
        for {
          num <- IO(n)
          prevNum <- sum_v2(n - 1)
        } yield num + prevNum
      }
    }

    println(sum_v2(20000).unsafeRunSync())
  }

  def exercise_06(): Unit = {
    import cats.effect.unsafe.implicits.global

    def fib(n: Int): IO[Int] = {
      if (n <= 0) IO.pure(0)
      else if (n == 1) IO.pure(1)
      else {
        for {
          b <- IO(fib(n - 1)).flatMap(identity)  // IO(...).flatten
          a <- IO.defer(fib(n - 2))
        } yield a + b
      }
    }

    val result = fib(10).unsafeRunSync()
    println(result)
  }

  def exercise_07(): Unit = {
    // composing multiple IOs (alternative to for-comp)
    import cats.syntax.apply._ // for mapN extension method
    import cats.effect.unsafe.implicits.global

    val io1 = IO(println("Step 1"))
    val io2 = IO(println("Step 2"))
    val io3 = IO(println("Step 3"))
    val io4 = IO(println("Step 4"))

    (io1, io2, io3, io4).mapN((_, _, _, _) => ()).unsafeRunSync()
  }

  // ------------------------------
//  exercise_00()
//  exercise_01()
//  exercise_02()
//  exercise_03()
//  exercise_04()
//  exercise_05()
//  exercise_06()
  exercise_07()
}
