package actions

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import slick.dbio.DBIOAction
import slick.lifted.Rep

import slick.driver.H2Driver.api._

object Main {

  // Tables -------------------------------------

  case class Album(
    artist : String,
    title  : String,
    year   : Int,
    rating : Rating,
    id     : Long = 0L)

  class AlbumTable(tag: Tag) extends Table[Album](tag, "albums") {
    def artist = column[String]("artist")
    def title  = column[String]("title")
    def year   = column[Int]("year")
    def rating = column[Rating]("rating")
    def id     = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def * = (artist, title, year, rating, id) <> (Album.tupled, Album.unapply)
  }

  lazy val AlbumTable = TableQuery[AlbumTable]



  // Schema actions -----------------------------

  val createTableAction =
    AlbumTable.schema.create

  val dropTableAction =
    AlbumTable.schema.drop



  // Select actions -----------------------------

  val selectAction =
    AlbumTable
      .filter(_.artist === "Keyboard Cat")
      .map(_.title)
      .result



  // Update actions -----------------------------

  val updateAction =
    AlbumTable
      .filter(_.artist === "Keyboard Cat")
      .map(_.title)
      .update("Even Greater Hits")

  val updateAction2 =
    AlbumTable
      .filter(_.artist === "Keyboard Cat")
      .map(a => (a.title, a.year))
      .update(("Even Greater Hits", 2010))



  // Delete actions -----------------------------

  val deleteAction =
    AlbumTable
      .filter(_.artist === "Justin Bieber")
      .delete



  // Insert actions -----------------------------

  val insertOneAction =
    AlbumTable += Album("Pink Floyd", "Dark Side of the Moon", 1978, Rating.Awesome )

  val insertAllAction =
    AlbumTable ++= Seq(
      Album( "Keyboard Cat"  , "Keyboard Cat's Greatest Hits" , 2009 , Rating.Awesome ),
      Album( "Spice Girls"   , "Spice"                        , 1996 , Rating.Good    ),
      Album( "Rick Astley"   , "Whenever You Need Somebody"   , 1987 , Rating.NotBad  ),
      Album( "Manowar"       , "The Triumph of Steel"         , 1992 , Rating.Meh     ),
      Album( "Justin Bieber" , "Believe"                      , 2013 , Rating.Aaargh  ))

  val insertThreeAlbums =
    AlbumTable ++= Seq(
      Album( "Queen", "Greatest Hits 1", 1980, Rating.Awesome),
      Album( "Queen", "Greatest Hits 2", 1981, Rating.Awesome),
      Album( "Queen", "Greatest Hits 3", 1982, Rating.Awesome)
    )

  val updateRatings =
    AlbumTable
      .filter(_.year >= 1990)
      .map(_.rating)
      .update(Rating.Meh)

  def deleteByArtist(artist: String) =
    AlbumTable
     .filter(_.artist === artist)
     .delete

  def addAlbum(artist: String, title: String, year: Int): DBIOAction[Unit, NoStream, Effect.All] =
    for {
      a <- AlbumTable.filter { a => a.artist === artist && a.year < year }.result
      r = if (a.isEmpty) Rating.Awesome else Rating.Meh
      _ <- AlbumTable += Album(artist, title, year, r)
    } yield ()

  // Database -----------------------------------

  val db = Database.forConfig("scalaxdb")



  // Let's go! ----------------------------------

  def exec[T](action: DBIO[T]): T = {
    Await.result(db.run(action), 2 seconds)
  }

  def main(args: Array[String]): Unit = {
    exec(createTableAction)
    exec(insertAllAction)
    exec(insertOneAction)
    exec(insertThreeAlbums)
    exec(AlbumTable.result).foreach(println)

    println("*****")
    exec(updateRatings)
    exec(AlbumTable.result).foreach(println)

    println("Artist to delete: ")
    val toDelete = io.StdIn.readLine()
    println(s"To delete: <$toDelete>")
    exec(AlbumTable.filter(_.artist === toDelete).result).foreach(println)

    println("+++++")
    exec(deleteByArtist(toDelete))
    exec(AlbumTable.result).foreach(println)

    println("-----")
    exec(
      addAlbum("Bono", "Album 1", 1990) andThen
      addAlbum("Bono", "Album 2", 1991) andThen
      AlbumTable.filter(_.artist === "Bono").result transactionally
    ).foreach(println)
  }
}
