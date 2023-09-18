package com.afecioru.study.cats.chapter2

import scala.util.{Failure, Success, Try}

object App005_UsingMonads extends App {

  // The Either can also be considered monadic, since it is right-biased
  // Often in various code-bases you will encounter type-aliases such as:

  type LoadingOr[T] = Either[String, T]
  type ErrorOr[T] = Either[Throwable, T]

  import cats.Monad
  import cats.instances.either._
  val loadingMonad = Monad[LoadingOr]
  val anEither = loadingMonad.pure(45) // LoadingOr[Int] === Right(45)
  val anotherEither = loadingMonad.flatMap(anEither) { value =>
    if (value < 100) Left("Still loading...") else Right(100)
  }

  //
  case class OrderStatus(orderId: Long, status: String)

  def getOrderStatus(orderId: Long): LoadingOr[OrderStatus] =
    Right(OrderStatus(orderId, "Ready to ship"))

  def trackLocation(orderStatus: OrderStatus): LoadingOr[String] = {
    if (orderStatus.orderId > 1000) Left("Not available yet...")
    else Right("Bucharest, RO")
  }

  val orderId = 100L
  val location1 = loadingMonad.flatMap(getOrderStatus(orderId))(trackLocation)

  // with extension methods
  import cats.syntax.flatMap._
  import cats.syntax.functor._
  val location2 = getOrderStatus(orderId).flatMap(trackLocation)

  val location3 = for {
    status <- getOrderStatus(orderId)
    location <- trackLocation(status)
  } yield location

  println("---------[ OUTPUT 1]-----------")
  println(location1)
  println(location2)
  println(location3)
  println("-------------------------------")


  /* Exercise 1: service layer impl. of a web app
    Requirements:
      - if the port/host are in the config map, return an M[Connection]
      - if payload.length < 10 issueRequest will return an M with message "request (payload) has been accepted"
      - provide concrete implementations for Option, Try, Future, Either
   */
  case class Connection(host: String, port: String)
  val config = Map(
    "host" -> "localhost",
    "port" -> "4040"
  )

  trait HttpService[M[_]] {
    def getConnection(cfg: Map[String, String]): M[Connection]
    def issueRequest(connection: Connection, payload: String): M[String]
  }

  implicit case object  HttpServiceOption extends HttpService[Option] {
    override def getConnection(cfg: Map[String, String]): Option[Connection] = {
      for {
        host <- cfg.get("host")
        port <- cfg.get("port")
      } yield Connection(host, port)
    }

    override def issueRequest(connection: Connection, payload: String): Option[String] =
      if (payload.length < 10) Some(s"request ($payload) has been accepted")
      else None
  }

  case object HttpServiceTry extends HttpService[Try] {
    override def getConnection(cfg: Map[String, String]): Try[Connection] = {
      (for {
        host <- cfg.get("host")
        port <- cfg.get("port")
      } yield Connection(host, port)) match {
        case Some(value) => Success(value)
        case None => Failure(new RuntimeException("invalid host/port configuration"))
      }
    }

    override def issueRequest(connection: Connection, payload: String): Try[String] =
      if (payload.length < 10) Success(s"request ($payload) has been accepted")
      else Failure(new RuntimeException("payload too large"))
  }

  case object HttpServiceEither extends HttpService[ErrorOr] {
    override def getConnection(cfg: Map[String, String]): ErrorOr[Connection] = {
      (for {
        host <- cfg.get("host")
        port <- cfg.get("port")
      } yield Connection(host, port)) match {
        case Some(value) => Right(value)
        case None => Left(new RuntimeException("invalid host/port configuration"))
      }
    }

    override def issueRequest(connection: Connection, payload: String): ErrorOr[String] =
      if (payload.length < 10) Right(s"request ($payload) has been accepted")
      else Left(new RuntimeException("payload too large"))
  }

  def getResponse[M[_]: Monad](httpService: HttpService[M])(payload: String): M[String] = {
    for {
      conn <- httpService.getConnection(config)
      response <- httpService.issueRequest(conn, payload)
    } yield response
  }

  val serviceType = "option"
  val httpService = serviceType match {
    case "option" => HttpServiceOption
    case "try" => HttpServiceTry
    case "either" => HttpServiceEither
    case _ => throw new UnsupportedOperationException(s"unsupported service type: $serviceType")
  }


  println("---------[ OUTPUT 2]-----------")
  println(HttpServiceOption.getConnection(config).flatMap { conn =>
    HttpServiceOption.issueRequest(conn, "Hi there!")
  })
  println(HttpServiceTry.getConnection(config).flatMap { conn =>
    HttpServiceTry.issueRequest(conn, "Hi there!")
  })
  println(HttpServiceEither.getConnection(config).flatMap { conn =>
    HttpServiceEither.issueRequest(conn, "Hi there!")
  })
  println("-------------------------------")
  println(getResponse(HttpServiceOption)("Hi there!"))
  println(getResponse(HttpServiceTry)("Hi there!"))
  println(getResponse(HttpServiceEither)("Hi there!"))
  println("-------------------------------")
}
