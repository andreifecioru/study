package com.afecioru.apps.twitbase

import org.scala_tools.time.Imports._
import com.afecioru.apps.twitbase.dao.{HBase, TwitDao, UsersDao}
import com.afecioru.apps.twitbase.model._

object TwitBaseApp extends App {
  val conn = HBase.createConnection()
  val usersDao = new UsersDao(conn)

  println("\nCreating a couple of users and saving them in HBase")
  Seq(
    User(
      userName = "andrei.fecioru",
      name = "Andrei Fecioru",
      email = "andrei.fecioru@gmail.com",
      password = "pass_one"
    ),

    User(
      userName = "ion.popescu",
      name = "Ion Popescu",
      email = "ion.popescu@gmail.com",
      password = "pass_two"
    )
  ).foreach(usersDao.addUser)

  println("\nGetting a user from HBase")
  usersDao.getUser("andrei.fecioru")
    .foreach(println)

  // Listing all users from HBase
  println("\nListing all users in HBase")
  usersDao.listUsers().foreach(println)

  val twitDao = new TwitDao(conn)
  val today = DateTime.now.withTimeAtStartOfDay()

  println("\nCreating some tweets")
  Seq(
    Twit(
      "andrei.fecioru",
      today.getMillis,
      "First tweet"
    ),

    Twit(
      "andrei.fecioru",
      (today + 1.hours).getMillis,
      "Second tweet"
    ),

    Twit(
      "ion.popescu",
      today.getMillis,
      "Random tweet"
    )
  ).foreach(twitDao.addTwit)


  println("\nListing Andrei's twits")
  twitDao.listTwitsForUser("andrei.fecioru").foreach(println)

  // increment the tweet count accordingly
  usersDao.incrementTweetsForUser("andrei.fecioru", 2L)
  usersDao.incrementTweetsForUser("ion.popescu", 1L)

  // Listing all users from HBase
  println("\nListing all users in HBase (to check incremented tweets)")
  usersDao.listUsers().foreach(println)

  conn.close()
  println("\n\nDone.")
}
