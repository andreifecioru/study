package com.afecioru.study.apps.coordination

import cats.effect.kernel.{Deferred, Ref}
import cats.effect.{IO, IOApp}
import com.afecioru.study.apps.IODebugWrapper

import scala.concurrent.duration._

object Defers extends IOApp.Simple {
  /*
    Deferred enables us to send "signals" between fibers. They are similar with promises:
    we can signal to another fiber that an operation is complete.
  */

  private val program1 = {
    // One-time produce-consumer

    def consumer(signal: Deferred[IO, Int]): IO[Unit] = for {
      _ <- IO.pure("[consumer] Waiting for data...")._debug
      data <- signal.get // blocks the consumer (semantic blocking)
      _ <- IO.pure(s"[consumer] Data received: $data")._debug
    } yield ()

    def producer(signal: Deferred[IO, Int]): IO[Unit] = for {
      _ <- IO.pure("[producer] Computing data...")._debug >> IO.sleep(1.second)
      data <- IO.pure(100)
      _ <- IO.pure(s"[producer] Data produced: $data")._debug
      _ <- signal.complete(data)
    } yield ()

    for {
      signal <- IO.deferred[Int]
      consumerFib <- consumer(signal).start
      producerFib <- producer(signal).start
      _ <- consumerFib.join
      _ <- producerFib.join
    } yield ()

  }

  private val program2 = {
    val chunks = Seq("one", " two", " three", " <EOF>")

    final case class Download(data: String = "", chunkCount: Int = 0)

    def downloader(download: Ref[IO, Download], signal: Deferred[IO, Boolean]): IO[Unit] = for {
      _ <- IO.pure("[downloader] Downloading data...")._debug
      _ <- IO.sleep(1.second)
      chunkIdx <- download.get.map(_.chunkCount)
      chunk = chunks(chunkIdx)
      downloadComplete = IO.pure { chunk == " <EOF>" }
      _ <- IO.pure(s"[downloader] Downloaded data: $chunk")._debug
      _ <- download.update(d => d.copy(data = d.data + chunk, chunkCount = d.chunkCount + 1))
      _ <- downloadComplete.flatMap { completed =>
        if (completed) signal.complete(true) else IO.unit
      }
      _ <- downloadComplete.flatMap { completed =>
        if (!completed) downloader(download, signal) else IO.unit
      }
    } yield ()

    def reporter(download: Ref[IO, Download], signal: Deferred[IO, Boolean]): IO[Unit] = for {
      _ <- IO.pure("[reporter] Waiting for the download to complete...")._debug
      _ <- signal.get
      _download <- download.get
      _ <- IO.pure(s"[reporter] Downloaded data: ${_download.data}")._debug
    } yield ()

    for {
      download <- IO.ref(Download())
      signal <- IO.deferred[Boolean]
      downloaderFib <- downloader(download, signal).start
      reporterFib <- reporter(download, signal).start
      _ <- downloaderFib.join
      _ <- reporterFib.join
    } yield ()
  }

  override def run: IO[Unit] =
    program2.void
}
