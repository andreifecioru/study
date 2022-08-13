package joins

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import slick.dbio.DBIOAction
import slick.profile.SqlAction

import slick.driver.H2Driver.api._

object Main {

  // Tables -------------------------------------

  case class Artist(
    name   : String,
    id     : Long = 0L)

  class ArtistTable(tag: Tag) extends Table[Artist](tag, "artists") {
    def name   = column[String]("name")
    def id     = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def * = (name, id) <> (Artist.tupled, Artist.unapply)
  }

  lazy val ArtistTable = TableQuery[ArtistTable]

  case class Album(
    artistId : Long,
    title    : String,
    year     : Int,
    rating   : Rating,
    id       : Long = 0L)

  class AlbumTable(tag: Tag) extends Table[Album](tag, "albums") {
    def artistId = column[Long]("artistId")
    def title    = column[String]("title")
    def year     = column[Int]("year")
    def rating   = column[Rating]("rating")
    def id       = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def * = (artistId, title, year, rating, id) <> (Album.tupled, Album.unapply)
  }

  lazy val AlbumTable = TableQuery[AlbumTable]



  // Setup --------------------------------------

  val createTablesAction =
    ArtistTable.schema.create andThen
    AlbumTable.schema.create

  val dropTablesAction =
    AlbumTable.schema.drop andThen
    ArtistTable.schema.drop

  val insertAllAction: DBIOAction[Unit, NoStream, Effect.Write] =
    for {
      keyboardCatId  <- ArtistTable returning ArtistTable.map(_.id) += Artist( "Keyboard Cat"   )
      spiceGirlsId   <- ArtistTable returning ArtistTable.map(_.id) += Artist( "Spice Girls"    )
      rickAstleyId   <- ArtistTable returning ArtistTable.map(_.id) += Artist( "Rick Astley"    )
      myMatesBandId  <- ArtistTable returning ArtistTable.map(_.id) += Artist( "My Mate's Band" )
      _              <- AlbumTable ++= Seq(
                          Album( keyboardCatId , "Keyboard Cat's Greatest Hits" , 2009 , Rating.Awesome ),
                          Album( spiceGirlsId  , "Spice"                        , 1996 , Rating.Good    ),
                          Album( spiceGirlsId  , "Forever"                      , 2000 , Rating.Meh     ),
                          Album( rickAstleyId  , "Whenever You Need Somebody"   , 1987 , Rating.Awesome ),
                          Album( rickAstleyId  , "Hold Me in Your Arms"         , 1988 , Rating.Good    ),
                          Album( rickAstleyId  , "Free"                         , 1991 , Rating.Meh     ),
                          Album( rickAstleyId  , "Body & Soul"                  , 1993 , Rating.Meh     ),
                          Album( rickAstleyId  , "Keep It Turned On"            , 2001 , Rating.Meh     ),
                          Album( rickAstleyId  , "Portrait"                     , 2005 , Rating.NotBad  ),
                          Album( rickAstleyId  , "My Red Book"                  , 2013 , Rating.Meh     ))
    } yield ()



  // Implicit joins -----------------------------

  val implicitInnerJoin: DBIOAction[Seq[(Artist, Album)], NoStream, Effect.Read] = {
    val query = for {
      artist <- ArtistTable
      album  <- AlbumTable if artist.id === album.artistId
    } yield (artist, album)

    query.result
  }



  // Explicit joins -----------------------------

  val explicitInnerJoin: DBIOAction[Seq[(Artist, Album)], NoStream, Effect.Read] =
    ArtistTable.join(AlbumTable)
      .on { case (artist, album) => artist.id === album.artistId }
      .result

  val ex1Action: DBIOAction[Seq[(Artist, Album)], NoStream, Effect.Read] = {
    val q = for {
      artist <- ArtistTable
      album <- AlbumTable if artist.id === album.artistId
    } yield (artist, album)

    q.sortBy { case (artist, album) => (artist.name, album.title) }.result
  }

  val ex2Action: DBIOAction[Seq[(Artist, Album)], NoStream, Effect.Read] = {
    ArtistTable.join(AlbumTable)
      .on { case (artist, album) => artist.id === album.artistId }
      .sortBy { case (artist, album) => (artist.name, album.year) }
      .result
  }


  // Database -----------------------------------

  val db = Database.forConfig("scalaxdb")



  // Let's go! ----------------------------------

  def exec[T](action: DBIO[T]): T =
    Await.result(db.run(action), 2 seconds)

  def resultsToString[T](message: String)(results: Seq[T]): String =
    message + results.mkString("\n  ", "\n  ", "")

  def main(args: Array[String]): Unit = {
    val everythingAction =
      createTablesAction andThen
      insertAllAction andThen
      DBIO.sequence(Seq(
//        implicitInnerJoin map resultsToString("Implicit inner join: "),
//        explicitInnerJoin map resultsToString("Explicit inner join: ")
        ex1Action map resultsToString("Exercise 1 result:"),
        ex2Action map resultsToString("Exercise 3 result:")
      ))

    exec(everythingAction.transactionally).foreach(println)
  }

}
