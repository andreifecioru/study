package com.afecioru.study.apps

import cats.effect.{IO, IOApp}

object IOSimpleApp extends IOApp.Simple {

  override def run: IO[Unit] = {
    val step1 = IO(println("Step one"))._debug
    val step2 = IO(println("Step two"))._debug
    val step3 = IO(println("Step three"))._debug
    val step4 = IO(println("Step four"))._debug

    step1 >> step2 >> step3 >> step4
  }
}
