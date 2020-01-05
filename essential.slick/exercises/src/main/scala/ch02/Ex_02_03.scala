package ch02

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import db._

//noinspection TypeAnnotation
object Ex_02_03 extends App with LazyLogging {
  import h2DbLayer._, profile.api._

  implicit val db = Database.forConfig("db.h2")

  val createTables = DBIO.seq(
    users.schema.create,
    rooms.schema.create,
    messages.schema.create
  )

  val m = Message("Sorry for the trouble")

  val actions = for {
    _ <- createTables

    andreiId <- insertUser += User("Andrei", "Fecioru")
    johnId <- insertUser += User("John", "Smith")

    _ <- messages ++= Seq(
      Message("Sorry for the trouble", fromId = andreiId, toId = johnId),
      Message("Hello there", fromId = johnId, toId = andreiId)
    )

    _ <- messages.filter(_.content.toLowerCase like "%sorry%").delete

    msgs <- messages.result
  } yield (msgs)

  exec(actions).foreach(println)
}
