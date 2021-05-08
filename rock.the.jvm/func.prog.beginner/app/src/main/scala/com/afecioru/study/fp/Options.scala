package com.afecioru.study.fp

import scala.util.Random

object Options extends App {

  /**
   * The Option[+T] type is a wrapper type over value of type T which encapsulates the concept of a possibly
   * missing value.
   *
   * It is a sum-type with only 2 values:
   *
   * sealed trait Option[+T]
   * case class Some[+T](value: T) extends Option[T]
   * case object None extends Option[Nothing]
   */

  val anOption = Some(4)

  // check if option has a value inside
  println(anOption.isDefined)
  println(anOption.isEmpty)

  // get the wrapped value
  println(anOption.get)      // throws exception of the option is empty
  println(None.getOrElse(5)) // get with default value

  // options can help us deal with unsafe APIs
  def unsafeMethod(): String = null
  def backupMethod(): String = "Some value"

  // this is wrong: Some should always wrap a non-null value
  val result = Some(unsafeMethod())
  println(result)

  // correct way: use the factory method provided by Option
  val result1 = Option(unsafeMethod())
  println(result1)

  // options are monadic: they can be composed (i.e. chained)
  def resultLength(in: String): Int = in.length

  // this will trigger a NPE
  // resultLength(unsafeMethod())

  // ... but this will not
  val result2 = Option(unsafeMethod()).map(resultLength).getOrElse(0)
  println(result2)

  // we can implement backup/recovery code paths
  val result3 = Option(unsafeMethod())
    .orElse(Option(backupMethod()))
    .map(resultLength)
    .getOrElse(0)
  println(result3)

  println("\n\n ----------------------------- \n\n")

  val config = Map(
    // fetched from elsewhere
    "host" -> "localhost",
    "port" -> "80"
  )

  case class Connection(host: String, port: String) {
    def connect(): Unit = println(s"Connected to $host:$port")
  }

  object Connection {
    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection(host, port))
      else None

    def defaultConnection: Option[Connection] =
      Some(new Connection("127.0.0.1", "8080"))
  }

  // establishing a connection

  // 1. load config params (maybe faulty)
  val connectionParams = for {
    host <- config.get("host")
    port <- config.get("port")
  } yield (host, port)

  println(connectionParams)

  // 2. try to establish a connection (10 times)
  (1 to 10).map { _ =>
    (for {
      (host, port) <- connectionParams
      connection <- Connection(host, port)
    } yield connection)
      // in case of failure use default connection
      .orElse(Connection.defaultConnection)
      // and finally connect
      .foreach(_.connect())
  }
}
