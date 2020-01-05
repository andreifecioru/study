package ch02

import com.typesafe.scalalogging.LazyLogging
import db._

//noinspection TypeAnnotation
object Ex_02_01 extends App with LazyLogging {
  import h2DbLayer._, profile.api._

  implicit val db = Database.forConfig("db.h2")

  val createTables = DBIO.seq(
    users.schema.create,
    rooms.schema.create,
    messages.schema.create
  )

  val createUser = users.map { user => (user.firstName, user.lastName) } += ("Andrei", "Fecioru")

  exec(
    createTables >>
    createUser >>
    users.result
  ).foreach(println)

}
