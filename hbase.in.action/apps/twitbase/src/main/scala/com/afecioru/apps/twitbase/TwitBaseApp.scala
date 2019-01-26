package com.afecioru.apps.twitbase


import com.afecioru.apps.twitbase.dao.{HBase, UsersDao}
import com.afecioru.apps.twitbase.model._

object TwitBaseApp extends App {
  val conn = HBase.createConnection()
  val usersDao = new UsersDao(conn)

  println("\nCreating a couple of users and saving them in HBase")
  usersDao.addUser(User(
    userName = "andrei.fecioru",
    name = "Andrei Fecioru",
    email = "andrei.fecioru@gmail.com",
    password = "pass_one",
    tweets = 100
  ))

  usersDao.addUser(User(
    userName = "ion.popescu",
    name = "Ion Popescu",
    email = "ion.popescu@gmail.com",
    password = "pass_two",
    tweets = 55
  ))

  println("\nGetting a user from HBase")
  usersDao.getUser("andrei.fecioru")
    .foreach(println)

  // Listing all users from HBase
  println("\nListing all users in HBase")
  usersDao.listUsers().foreach(println)

  conn.close()
  println("\n\nDone.")
}
