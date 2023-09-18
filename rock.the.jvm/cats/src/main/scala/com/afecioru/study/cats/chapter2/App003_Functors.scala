package com.afecioru.study.cats.chapter2

import jdk.internal.platform.Container

import scala.util.Try

object App003_Functors extends App {

  val nums = (1 to 10).toList
  val maybeNum = Option(10)
  val tryNum= Try(10)

  // Functors are higher-kinded types that generalize the "map"
  // operation over containers of various kind

  // Simplified definition
  trait SimpleFunctor[F[_]] {
    def map[A, B](container: F[A])(f: A => B): F[B]
  }

  import cats.Functor
  import cats.instances.list._
  import cats.instances.option._
  import cats.instances.try_._ // notice the try_ import to avoid collision with the keyword try

  val listFunctor = Functor[List]
  val optionFunctor = Functor[Option]
  val tryFunctor = Functor[Try]

  println("-------[ OUTPUT 1 ]-----------")
  println(listFunctor.map(nums)(_ + 1))
  println(optionFunctor.map(maybeNum)(_ + 1))
  println(tryFunctor.map(tryNum)(_ + 1))
  println("------------------------------\n\n")

  // Generalizing an API
  def do10x[F[_] : Functor](container: F[Int]): F[Int] = {
    val functor = implicitly(Functor[F])
    functor.map(container)(_ * 10)
  }


  println("-------[ OUTPUT 2 ]-----------")
  println(do10x(nums))
  println(do10x(maybeNum))
  println(do10x(tryNum))
  println("------------------------------\n\n")

  // Exercise 1: functor instance implementation for custom type: binary tree
  sealed trait Tree[+T]
  final case class Leaf[+T](value: T) extends Tree[T]
  final case class Branch[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T]

  implicit object TreeFunctor extends Functor[Tree] {
    override def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = {
      tree match {
        case Leaf(value) => Leaf(f(value))
        case Branch(value, left, right) =>
          Branch(f(value), map(left)(f), map(right)(f))
      }
    }
  }

  // NOTE: we need to explicitly specify the type Tree[Int] because cats TCs are invariant
  // (i.e. we don't have a functor for Branch subtype)
  val sampleTree: Tree[Int] = Branch(
    1,
    Branch(
      2,
      Branch(3, Leaf(4), Leaf(5)),
      Branch(4, Leaf(6), Leaf(7))
    ),
    Branch(
      8,
      Branch(9, Leaf(10), Leaf(11)),
      Branch(12, Leaf(13), Leaf(14))
    )
  )

  val anotherTree = Branch( // this is of type Branch - we need to specify type Tree at call-site
    1,
    Branch(
      2,
      Branch(3, Leaf(4), Leaf(5)),
      Branch(4, Leaf(6), Leaf(7))
    ),
    Branch(
      8,
      Branch(9, Leaf(10), Leaf(11)),
      Branch(12, Leaf(13), Leaf(14))
    )
  )

  // Optionally we can use smart constructors for the Tree
  object Tree {
    def leaf[T](value: T): Tree[T] = Leaf(value)
    def branch[T](value: T, left: Tree[T], right: Tree[T]): Tree[T] = Branch(value, left, right)
  }

  val yetAnotherTree = {
    import Tree._
    branch(
      1,
      branch(
        2,
        branch(3, leaf(4), leaf(5)),
        branch(4, leaf(6), leaf(7))
      ),
      branch(
        8,
        branch(9, leaf(10), leaf(11)),
        branch(12, leaf(13), leaf(14))
      )
    )}

  println("-------[ OUTPUT 3 ]-----------")
  println(do10x(sampleTree))
  println(do10x[Tree](anotherTree)) // Gotcha: we need to specify the Tree type (cats TCs are invariant)
  // when using smart ctors, this problem goes away
  println(do10x(yetAnotherTree))
  println("------------------------------\n\n")

  // Functor's extension method: map
  import cats.syntax.functor._

  def do100x[F[_]: Functor](container: F[Int]): F[Int] = container.map(_ * 100)

  println("-------[ OUTPUT 4 ]-----------")
  println(do100x(sampleTree))
  println("------------------------------\n\n")

}
