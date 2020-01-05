import scala.language.implicitConversions

import slick.dbio.DBIO
import slick.basic.BasicBackend
import slick.jdbc.JdbcProfile
import slick.lifted.MappedTo

import scala.concurrent.Await
import scala.concurrent.duration._

package object db {
  def exec[T](action: DBIO[T])(implicit db: BasicBackend#DatabaseDef): T = Await.result(db.run(action), 2 seconds)

  case class PK[T](value: Long) extends AnyVal with MappedTo[Long]

  trait DbProfile {
    val profile: JdbcProfile
  }

  class DbLayer(val profile: JdbcProfile) extends DbProfile with ChatModule

  val h2DbLayer = new DbLayer(slick.jdbc.H2Profile)

  //noinspection TypeAnnotation
  trait ChatModule { self: DbProfile=>
    import profile.api._

    // ---------------------[ USER TABLE ]--------------------
    val TABLE_NAME_USER = "user"

    final case class User(firstName: String,
                          lastName: String,
                          id: PK[UserTable] = PK(0L))

    class UserTable(tag: Tag) extends Table[User](tag, TABLE_NAME_USER) {
      val COL_NAME_ID = "id"
      val COL_NAME_FIRST_NAME = "first_name"
      val COL_NAME_LAST_NAME = "last_name"

      def id = column[PK[UserTable]](COL_NAME_ID, O.AutoInc, O.PrimaryKey)
      def firstName = column[String](COL_NAME_FIRST_NAME)
      def lastName = column[String](COL_NAME_LAST_NAME)

      def * = (firstName, lastName, id).mapTo[User]
    }

    lazy val users = TableQuery[UserTable]
    lazy val insertUser = users returning users.map(_.id)

    // ---------------------[ ROOM TABLE ]--------------------
    val TABLE_NAME_ROOM = "room"

    final case class Room(title: String,
                          id: PK[RoomTable])

    class RoomTable(tag: Tag) extends Table[Room](tag, TABLE_NAME_ROOM) {
      val COL_NAME_TITLE = "title"
      val COL_NAME_ID = "id"

      def id = column[PK[RoomTable]](COL_NAME_ID, O.AutoInc, O.PrimaryKey)
      def title = column[String](COL_NAME_TITLE, O.Unique)

      def * = (title, id).mapTo[Room]
    }

    lazy val rooms = TableQuery[RoomTable]
    lazy val insertRoom = rooms returning rooms.map(_.id)

    // ---------------------[ MESSAGE TABLE ]--------------------
    val TABLE_NAME_MESSAGE = "message"

    final case class Message(content: String,
                             fromId: PK[UserTable] = PK(0L),
                             toId: PK[UserTable] = PK(0L),
                             roomId: Option[PK[RoomTable]] = None,
                             id: PK[MessageTable] = PK(0L))

    class MessageTable(tag: Tag) extends Table[Message](tag, TABLE_NAME_MESSAGE) {
      val COL_NAME_CONTENT = "content"
      val COL_NAME_FROM_ID = "from_id"
      val COL_NAME_TO_ID = "to_id"
      val COL_NAME_ROOM_ID = "room_id"
      val COL_NAME_ID = "id"
      val FK_NAME_FROM = "from_fk"
      val FK_NAME_TO = "to_fk"
      val FK_NAME_ROOM = "room_fk"

      def id = column[PK[MessageTable]](COL_NAME_ID, O.AutoInc, O.PrimaryKey)
      def fromId = column[PK[UserTable]](COL_NAME_FROM_ID)
      def toId = column[PK[UserTable]](COL_NAME_TO_ID)
      def roomId = column[Option[PK[RoomTable]]](COL_NAME_ROOM_ID)
      def content = column[String](COL_NAME_CONTENT)

      def * = (content, fromId, toId, roomId, id).mapTo[Message]

      def from = foreignKey(FK_NAME_FROM, fromId, users)(_.id)
      def to = foreignKey(FK_NAME_TO, toId, users)(_.id)
      def room = foreignKey(FK_NAME_ROOM, roomId, rooms)(_.id)
    }

    lazy val messages = TableQuery[MessageTable]
    lazy val insertMessage = messages returning messages.map(_.id)
  }
}
