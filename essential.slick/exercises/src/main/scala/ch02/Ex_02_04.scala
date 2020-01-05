package ch02

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import db._

//noinspection TypeAnnotation
object Ex_02_04 extends App with LazyLogging {
  import h2DbLayer._, profile.api._

  implicit val db = Database.forConfig("db.h2")

  val createTables = DBIO.seq(
    users.schema.create,
    rooms.schema.create,
    messages.schema.create
  )

  val insertData = for {
    andreiId <- insertUser += User("Andrei", "Fecioru")
    johnId <- insertUser += User("John", "Smith")

    _ <- messages ++= Seq(
      Message("Andrei One", fromId = andreiId, toId = johnId),
      Message("John One", fromId = johnId, toId = andreiId),
      Message("Andrei Two", fromId = andreiId, toId = johnId),
      Message("John Two", fromId = johnId, toId = andreiId),
      Message("Andrei Three", fromId = andreiId, toId = johnId),
      Message("John Three", fromId = johnId, toId = andreiId),
      Message("Andrei Four", fromId = andreiId, toId = johnId),
    )
  } yield (andreiId, johnId)

  val actions = createTables
    .andThen(insertData)
    .map {
      case (andreiId, johnId) =>
        messages
          .filter(_.fromId === andreiId)
          .sortBy(_.id.asc)
          .map(_.id)
          .take(2)
    }
    .flatMap { targetIds =>
      messages.filter(_.id in targetIds).delete
    }

  exec(actions >> messages.result).foreach(println)
}
