package fun

import model.Box

import scala.language.higherKinds

trait Functor[F[_]] {
  def map[A, B](in: F[A])(f: A => B): F[B]
}

object Functor {
  def apply[F[_]](implicit f: Functor[F]): Functor[F] = f
}

object FunctorInstances {
  implicit val boxFunctor: Functor[Box] = new Functor[Box] {
    override def map[A, B](in: Box[A])(f: A => B): Box[B] = Box(f(in.value))
  }
}
