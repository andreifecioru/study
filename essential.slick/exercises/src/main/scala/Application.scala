import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.ExecutionContext.Implicits.global
import slick.basic.BasicBackend

import db._


object Application extends App with LazyLogging {
  import h2DbLayer._, profile.api._
  implicit val db: BasicBackend#DatabaseDef = Database.forConfig("db.h2")

  val action = for {
    _ <- users.schema.create
    count <- users.length.result
  } yield count

  val result = exec(action)

  logger.info(s"Result: $result")
}
