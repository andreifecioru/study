package com.afecioru.study.cats.chapter2

import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._


object App007_MonadTransformers extends App {

  // Problem: you have nested monads (ex: List[Option[_]]) and you want
  // to be able to do operations on this structure w/o needing to unwrap the inner monad

  def sumAllOptions(list: List[Option[Int]]): Int =
    list.map { // here we need to unwrap the inner monad
      _.getOrElse(0)
    }.sum

  // This is where monad transformers can help
  import cats.data.OptionT
  import cats.instances.list._   // fetches Monad[List]
  import cats.instances.future._ // fetches Monad[Future]
  import cats.syntax.option._

  val listOfNumbers: OptionT[List, Int] = OptionT(List(1.some, 2.some, none[Int]))
  val listOfChars: OptionT[List, Char] = OptionT(List('a'.some, 'b'.some, 'c'.some))

  // OptionT implements map/flatMap so we can do for-comps
  val listOfTuples: OptionT[List, (Int, Char)] = for {
    num <- listOfNumbers
    char <- listOfChars
  } yield num -> char

  println("---------[ OUTPUT 1]-----------")
  println(listOfTuples.value)
  println("-------------------------------")

  // Monad transformer for Either
  import cats.data.EitherT

  val listOfEithers: EitherT[List, String, Int] = EitherT(List(Left("something wrong"), Right(10), Right(100)))

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
  val futureOfEither: EitherT[Future, String, Int] = EitherT.right(Future(5))


  /* Exercise 1
    We have a cluster of servers which will receive a traffic spike following a media event.
    Each server can support a certain amount of bandwidth (expressed as an int).
    We want to allocate 2 of the nodes to cope with the traffic spike.
    To cope with the traffic we want the combined bandwidth of the 2 servers to exceed 250.
  */
  val bandwidthTable = Map(
    "server1.com" -> 50,
    "server2.com" -> 300,
    "server3.com" -> 170
  )

  type AsyncResponse[T] = EitherT[Future, String, T] // wrapper over Future[Either[String, Int]]

  def getBandwidth(server: String): AsyncResponse[Int] = {
    bandwidthTable.get(server) match {
      case None => EitherT.left(Future(s"Server $server is unreachable"))
      case Some(bw) => EitherT.right(Future(bw))
    }
  }

  def canHandleTrafficSpike(server1: String, server2: String): AsyncResponse[Boolean] = {
    for {
      bw1 <- getBandwidth(server1)
      bw2 <- getBandwidth(server2)
    } yield (bw1 + bw2) >= 250
  }

  def generateTrafficReport(server1: String, server2: String): AsyncResponse[String] = {
    canHandleTrafficSpike(server1, server2).transform {
      case Left(reason) => Left(s"$server1 and $server2 can't handle the traffic spike: $reason")
      case Right(false) => Left(s"$server1 and $server2 can't handle the traffic spike: insufficient bandwidth")
      case Right(true) => Right(s"$server1 and $server2 can handle the traffic spike")
    }
  }

  println("---------[ OUTPUT 2]-----------")
  println(Await.result(getBandwidth("server1.com").value, 1.second))
  println(Await.result(getBandwidth("server5.com").value, 1.second))

  println("---")

  println(Await.result(canHandleTrafficSpike("server1.com", "server2.com").value, 1.second))
  println(Await.result(canHandleTrafficSpike("server1.com", "server3.com").value, 1.second))
  println(Await.result(canHandleTrafficSpike("server1.com", "server5.com").value, 1.second))

  println("---")

  println(Await.result(generateTrafficReport("server1.com", "server2.com").value, 1.second))
  println(Await.result(generateTrafficReport("server1.com", "server3.com").value, 1.second))
  println(Await.result(generateTrafficReport("server1.com", "server5.com").value, 1.second))
  println("-------------------------------")


}
