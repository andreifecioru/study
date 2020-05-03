package com.afecioru.apps

// must have exactly 1 val param over another value class
class Title(val title: String) extends AnyVal

class Author(val name: String) extends AnyVal

// can be a case class
case class Price(price: Double) extends AnyVal {
  // you can customize the class with methods
  override def toString: String = "$" + price.toString
}

case class Book(
  title: Title,
  author: Author,
  price: Price
)


object ValueTypesApp extends App {
  // when we create a book, we don't have to use simple strings
  // and hope the order matches, we can rely on the type checker
  val book = Book(
    new Title("A book title"),
    new Author("Some author"),
    Price(10.99)
  )

  println(book)
}
