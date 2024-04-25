package com.afecioru.study.apps

object FunctorApp extends App {

  // type class
  trait Functor[C[_]] {
    def mapN[A, B](container: C[A])(f: A => B): C[B]
  }

  // instance
  implicit val listFunctor = new Functor[List] {
    override def mapN[A, B](container: List[A])(f: A => B): List[B] = container.map(f)
  }

  // API
  implicit class ListFunctorOps[T](list: List[T]) {
    def mapN[U](f: T => U)(implicit functor: Functor[List]): List[U] =
      functor.mapN(list)(f)
  }

  val result = List(1,2,3,4).mapN(_ * 10)

  println(result)

}
