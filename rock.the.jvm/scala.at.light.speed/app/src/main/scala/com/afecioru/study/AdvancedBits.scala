package com.afecioru.study

import scala.language.postfixOps

import scala.util.{Failure, Success, Try}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object AdvancedBits extends App {
  // lazy evaluation
  lazy val myValue = { // this block is evaluated once on first use
    println("Side effect!")
    2
  }

  println("Check 1")
  println(s"Value $myValue")
  println(s"Value $myValue")

  // pseudo-collections: Option, Try
  def methodWithNull(): Option[String] = Some("hello")
  methodWithNull().map(println).getOrElse(println("Nothing"))

  def methodWithException(): Try[String] = Try {
    throw new RuntimeException("Boom")
  }

  val result = methodWithException() match {
    case Success(value) => s"Got $value back"
    case Failure(exception) => s"Got ${exception.getMessage}"
  }
  println(result)

  // or
  val result1 = methodWithException()
    .map(msg => println(s"Got $msg back"))
    .getOrElse("Failed")
  println(result1)

  // Async programming (futures)

  val task = Future {
    println("Loading...")
    Thread.sleep(5000)
    println("Done.")

    "some data"
  }

  task.foreach(println)

  // wait for the task to complete
  Await.result(task, 10 seconds)

  // Implicit basics

  // Implicit args
  def add(value: Int)(implicit to: Int): Int = value + to

  implicit val to: Int = 10 // type annotation is recommended for implicit definitions
  println(add(2)) // 12

  // Implicit conversions
  implicit class MyRichInt(n: Int) {
    def isEven: Boolean = n % 2 == 0
  }

  println(23 isEven) // 23 is converted to MyRichInt behind the scenes
}
