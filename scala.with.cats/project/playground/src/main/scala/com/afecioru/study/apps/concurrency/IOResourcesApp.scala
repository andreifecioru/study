package com.afecioru.study.apps.concurrency

import cats.effect.{IO, IOApp, Resource}
import com.afecioru.study.apps.IODebugWrapper

import scala.concurrent.duration._

object IOResourcesApp extends IOApp.Simple {
  private case class AppConfig(path: String) {
    def getUrl: String = "www.example.com"
    def close(): IO[String] = IO.pure(s"Closing config file at: $path")._debug
  }

  private case class Connection(url: String) {
    def open(): IO[String] = IO.pure(s"Opening URL: $url")._debug
    def close(): IO[String] = IO.pure(s"Closing URL: $url")._debug
  }

  val program1 = {

    // Avoiding resource leaks by making sure `close()` is invoked
    for {
      connection <- IO.pure(Connection("www.example.com"))
      // open the connection on a separate thread
      // but make sure it gets closed
      fib <- {
        connection.open() >> IO.sleep(5.seconds) >> connection.close()
      }
        .onCancel(connection.close().void)
        .start
      _ <- {
        IO.sleep(2.seconds) >> fib.cancel >> IO.pure("Connection cancelled")._debug
      }.start
      result <- fib.join
    } yield result
  }

  val program2 = {
    /*
      The "bracket" pattern for resource acquisition and release:
        1. acquire the resource
        2. call `bracket()`
          - 1st param: what to do with the resource
          - 2nd param: how to release it
    */

    val openConnection = IO(Connection("www.example.com"))
      .bracket(_.open())(_.close().void)

    for {
      fib <- openConnection.start
      _ <- IO.sleep(1.seconds) >> fib.cancel
    } yield ()
  }

  val exercise_01 = {
    // Open a file and read the lines (one every 100ms)
    import scala.io.Source

    val readFile = IO(Source.fromFile("/Users/afecioru/.zshrc"))
      .bracket(source => {
        IO(source.getLines().toList)
          .flatMap { lines =>
            import cats.Traverse
            Traverse[List].traverse(lines) { line =>
              IO.sleep(100.millis) >> IO.pure(line)._debug
            }
          }
      })(source => IO {
        source.close(); "Closing file..."
      }._debug.void)

    for {
      fib <- readFile.start
      _ <- IO.sleep(5.seconds) >> IO("Cancelling reading file...")._debug >> fib.cancel
      result <- fib.join
    } yield result
  }

  val program3 = {
    // The "resource" pattern: acquisition and release are separate from usage

    val openConnectionResource = Resource.make { // resource acquisition
      for {
        conn <- IO.pure(Connection("www.example.com"))
        _ <- conn.open()
      } yield conn
    } { // resource release
      conn => conn.close().void
    }

    // resource use (in another part of the program)
    for {
      fib <- openConnectionResource.use { c => IO.pure(s"Working with URL: ${c.url}")._debug >> IO.never }.start
      _ <- IO.sleep(5.seconds) >> fib.cancel
    } yield ()
  }

  val exercise_02 = {

    def readFile(path: String, timeout: FiniteDuration): IO[Unit] = {
      import scala.io.Source
      val fileAcquisition = (path: String) => IO.pure(s"Opening file: $path")._debug >> IO(Source.fromFile(path))
      val fileRelease = (source: Source) => IO.pure(s"Closing file: $path")._debug >> IO(source.close()).void
      val fileResource = Resource.make(fileAcquisition(path))(fileRelease)

      def readLine(lines: Iterator[String]): IO[Unit] = {
        if (lines.hasNext) IO.sleep(timeout) >> IO(lines.next())._debug >> readLine(lines)
        else IO.unit
      }

      for {
        fib <- fileResource.use(source => readLine(source.getLines())).start
        _ <- IO.sleep(5.seconds) >> IO.pure("Canceling reading file")._debug >> fib.cancel
      } yield ()
    }

    readFile("/Users/afecioru/.zshrc", 100.millis)
  }

  val program4 = {
    // nested resources using the 'bracket' pattern
    def openConnection: IO[Nothing] = IO(AppConfig("/path/to/config.txt")).bracket { appConfig =>
      IO(Connection(appConfig.getUrl)).bracket { conn =>
        conn.open() >> IO.never
      }(_.close().void)
    }(_.close().void)

    for {
      fib <- openConnection.start
      _ <- IO.sleep(5.seconds) >> IO.pure("Canceling...")._debug >> fib.cancel
      _ <- fib.join
    } yield ()
  }

  val program5 = {
    // nested resources using the 'resource' pattern
    def configFileResource(path: String): Resource[IO, AppConfig] =
      Resource.make { IO.pure(s"Opening config file at: $path")._debug >> IO(AppConfig(path)) } { _.close().void }

    def connectionResource(url: String): Resource[IO, Connection] =
      Resource.make { IO(Connection(url)) } { _.close().void }

    val conn = for {
      cfgFile <- configFileResource("/path/to/config.txt")
      conn <- connectionResource(cfgFile.getUrl)
    } yield conn

    for {
      fib <- conn.use { conn =>
        conn.open() >> IO.never
      }.start
      _ <- IO.sleep(5.seconds) >> IO.pure("Cancelling...")._debug >> fib.cancel
      _ <- fib.join
    } yield ()
  }

  override def run: IO[Unit] =
    program5.void
}
