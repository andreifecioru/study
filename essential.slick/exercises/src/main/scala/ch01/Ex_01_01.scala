package ch01

import com.typesafe.scalalogging.LazyLogging

import db._

//noinspection TypeAnnotation
object Ex_01_01 extends App with LazyLogging {
  import h2DbLayer._, profile.api._

  implicit val db = Database.forConfig("db.h2")

  val createTables = DBIO.seq(
    users.schema.create,
    rooms.schema.create,
    messages.schema.create
  )

  val createUsers = users ++= Seq(
    User("Andrei", "Fecioru"),
    User("Maria", "Popescu")
  )

  exec(
    createTables >>
    createUsers >>
    users.result
  ).foreach(println)



}
