package ch02

import com.typesafe.scalalogging.LazyLogging
import db._

//noinspection TypeAnnotation
object Ex_02_02 extends App with LazyLogging {
  import h2DbLayer._, profile.api._

  implicit val db = Database.forConfig("db.h2")

  val createTables = DBIO.seq(
    users.schema.create,
    rooms.schema.create,
    messages.schema.create
  )

  val userList = List(
    User("Andrei", "Fecioru"),
    User("John", "Smith")
  )

  val insertUsers = users returning users.map(_.id) into { (user, id) =>
    user.copy(id=id)
  }

  exec(
    createTables >>
    (insertUsers ++= userList)
  ).foreach(println)
}
