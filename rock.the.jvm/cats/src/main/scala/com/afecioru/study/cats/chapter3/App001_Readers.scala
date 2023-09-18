package com.afecioru.study.cats.chapter3

object App001_Readers extends App {

  /*
    Problem setup

    We have a multi-layered application:
      - DB layer
      - HTTP service layer
      - business logic layer

    These layers need to be configured from an initial configuration object
    (fueled by a config file for example).
   */

  final case class Configuration(
    // DB layer config
    dbUsername: String,
    dbPasswd: String,

    // HTTP service layer config
    host: String,
    port: Int,

    // email service layer config
    replyTo: String,

    // business logic layer config
    nThreads: Int
  )

  // DB layer model
  final case class DbConnection(username: String, passwd: String) {
    def getOrderStatus(orderId: Int): String = "dispatched"

    def getLastOrderId(username: String): Int = 12345

    def getEmail(username: String): String = s"$username@email.com"
  }

  final case class HttpService(host: String, port: Int) {
    def startServer(): Unit =
      println(s"Server started: $host:$port")
  }

  final case class InternalProcess(nThreads: Int) {
    def execute(): Unit =
      println(s"Running internal process on $nThreads threads.")
  }

  /*
    The Reader pattern
      1. create the initial config data structure (at app. bootstrap phase)
      2. create a reader which specifies wrapping the config data structure
      3. use map & flatMap on the reader to produce derived info
      4. when you need the final piece of info, call run on the reader passing the initial
         config data structure
   */

  // 1. create the initial config data structure
  val config = Configuration(
    dbUsername = "admin", dbPasswd = "password", host = "localhost", port = 1234,
    replyTo = "noreply@shop.com", nThreads = 8
  )

  // 2. create a reader wrapper over the config data structure
  import cats.data.Reader

  val dbReader: Reader[Configuration, DbConnection] = Reader { conf =>
    DbConnection(username = conf.dbUsername, passwd = conf.dbPasswd)
  }
  val dbConn: DbConnection = dbReader.run(config)

  // 3. use map & flatMap on reader to produce derived info
  val orderStatusReader: Reader[Configuration, String] =
    dbReader.map(_.getOrderStatus(55))

  // 4. call run on reader passing the initial config data structure
  val orderStatus: String = orderStatusReader.run(config)

  // Additional examples
  val lastOrderIdReader = dbReader
    .map(_.getLastOrderId("admin"))
    .flatMap(orderId => dbReader.map(_.getOrderStatus(orderId)))

  // Same with for-comps
  val lastOrderIdForCompReader = for {
    orderId <- dbReader.map(_.getLastOrderId("admin"))
    orderStatus <- dbReader.map(_.getOrderStatus(orderId))
  } yield orderStatus

  val lastOrderStatus = lastOrderIdReader.run(config)

  // Exercise 1: adding an e-mail service
  final case class EmailService(replyTo: String) {
    def sendEmail(address: String, contents: String): String =
      s"From: $replyTo | To: $address | $contents"
  }

  val emailReader = Reader[Configuration, EmailService] { conf => EmailService(conf.replyTo) }

  // Send a confirmation email for the last order a user has placed
  val username = "admin"
  val emailContentsReader = for {
    orderId <- dbReader.map(_.getLastOrderId(username))
    orderStatus <- dbReader.map(_.getOrderStatus(orderId))
    emailAddress <- dbReader.map(_.getEmail(username))
    emailContents = s"Your last order has the status: ($orderStatus)"
    emailContents <- emailReader.map(_.sendEmail(emailAddress, emailContents))
  } yield emailContents

  val emailContents = emailContentsReader.run(config)

  println("----------[ OUTPUT 1 ]------------")
  println(emailContents)
  println("----------------------------------")

}
