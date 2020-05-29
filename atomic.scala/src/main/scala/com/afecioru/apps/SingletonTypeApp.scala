package com.afecioru.apps

object SingletonTypeApp extends App {
  val andrei: String = "andrei"
  val radu: String = "radu"

  type NameOfAndrei = andrei.type

  // allowed assignments
  val name1: NameOfAndrei = andrei
  val name2: NameOfAndrei = null

  // this is not allowed
  // val name3: NameOfAndrei = radu

  // objects are singletons too (they have a corresponding singleton-type)
  object Box

  // define a method which takes a param of type `Box`
  def f(box: Box.type): Unit = ???

  trait Log {
    def debug(message: String): Unit = println(s"[DEBUG] $message")
  }

  object LogSingleton extends Log
  class LogClass extends Log

  trait LoggingApp {
    // abstract type which can only be satisfied by a singleton
    type T <: Log with Singleton

    val logger: T

    def run(): Unit = {
      logger.debug("Running the app")
    }
  }

  // this works because T points to the singleton-type of the
  // `LogSingleton` object
  val app: LoggingApp = new LoggingApp {
    override type T = LogSingleton.type
    override val logger: T = LogSingleton
  }

  // this does not work (LogClass is not a singleton)
//  val app2: LoggingApp = new LoggingApp {
//    override type T = LogClass
//    override val logger: LogClass = new LogClass
//  }

  app.run()

  class A {
    def f1: this.type = this
  }

  class B extends A {
    def f2: this.type = this
  }

  val abc = new B
  abc.f1.f2
}
