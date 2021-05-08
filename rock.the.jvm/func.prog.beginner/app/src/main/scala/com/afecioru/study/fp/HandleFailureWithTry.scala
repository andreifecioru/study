package com.afecioru.study.fp

import scala.util.{Failure, Random, Success, Try}

object HandleFailureWithTry extends App {

  /**
   * Dealing with failure in Scala can be done with try/catch expressions.
   *
   * However, the try/catch paradigm is more of a Java construct. These expressions cannot be chained/composed.
   *
   * Scala introduces yet another monadic sum type called Try:
   *
   * sealed trait Try[+T]
   * case class Success[+T](value: T) extends Try[T]
   * case class Failure[+T](exception: T) extends Try[T]
   *
   * The only two sub-types of the Try trait wrap over the 2 possible values that can materialize when dealing with
   * code that can possible blow up: Success(value) and Failure(exception)
   */

  def unsafeMethod(): Int = {
    val random = new Random(System.nanoTime())

    if (random.nextBoolean()) 42
    else throw new NullPointerException
  }

  def safeDefault(): Int = -1

  // the factory method in the companion object of Try receives a by-name param
  // so we can pass blocks of code to it

  val result = for (_ <- 1 to 10) yield {
    Try {
      unsafeMethod()
    } match {
      case Success(value) => s"Got: $value"
      case Failure(e) => s"ERROR: $e"
    }
  }
  result.foreach(println)

  // Try is monadic and supports the chaining via map/flatMap as well as for-comps
  Try(unsafeMethod())
    .orElse(Try(safeDefault()))
    .foreach(v => println(s"Final result is: $v"))


  println("Done.")

  // map, flatMap, filter
  val aSuccess = Success(1)
  println(aSuccess.map(_ + 1))
  println(aSuccess.flatMap(v => Success(v * 10)))
  // filter may transform the Success into a Failure if the predicate does not hold for the value
  // wrapped inside the Success
  println(aSuccess.filter(_ % 2 == 0))

  println("\n\n-------------------------------------\n\n")

  val hostname = "localhost"
  val port = "port"

  def renderHTML(page: String): Unit = println(page)

  class Connection {
    val rand = new Random(System.nanoTime())

    def get(url: String): String =
      if (rand.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted.")
  }

  case object HttpService {
    private[this] val rand = new Random(System.nanoTime())

    def connect(host: String, port: String): Connection =
      if (rand.nextBoolean()) new Connection()
      else throw new RuntimeException("Port busy.")
  }


  def fetchPageForComp(times: Int): Unit = {
    for {
      idx <- 1 to times
      connection <- Try(HttpService.connect(hostname, port))
      page <- Try(connection.get("http://example.com"))
    } {
      println(s"Iteration: $idx")
      renderHTML(page)
    }
  }
  fetchPageForComp(10)

  println("\n\n-----------------------------------\n\n")

  def fetchPageChaining(times: Int): Unit = {
    (1 to times).foreach { idx =>
      Try(HttpService.connect(hostname, port))
        .flatMap(connection => Try(connection.get("http://example.com")))
        .foreach { page =>
          println(s"Iteration: $idx")
          renderHTML(page)
        }
    }
  }
  fetchPageChaining(10)
}
