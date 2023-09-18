package com.afecioru.study.cats.chapter2

import scala.annotation.tailrec

object App006_CustomMonads extends App {

  import cats.Monad

  // Custom implementation of Monad[Option]

  implicit object OptionMonad extends Monad[Option] {
    override def pure[A](x: A): Option[A] = Option(x)

    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
      fa.flatMap(f)

    // The Monad also defines a set of "iteration" APIs which require
    // an implementation for the method tailRecM
    @tailrec // must be tail-recursive
    override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] = {
      // We apply the f function in a tail-recursive fashion until f(a) = Right[B]
      f(a) match {
        case None => None
        case Some(Right(b)) => Some(b)
        case Some(Left(a)) => tailRecM(a)(f)
      }
    }
  }

  // Exercise 1: implement a custom monad for the identity type
  type Identity[T] = T

  implicit object IdentityMonad extends Monad[Identity] {
    override def pure[A](a: A): Identity[A] = a

    override def flatMap[A, B](a: Identity[A])(f: A => Identity[B]): Identity[B] =
      f(a)

    @tailrec
    override def tailRecM[A, B](a: A)(f: A => Identity[Either[A, B]]): Identity[B] = f(a) match {
      case Right(b) => b
      case Left(a) => tailRecM(a)(f)
    }
  }

  // Exercise 2: implement a custom Monad for binary Tree defined like below
  // NOTE: no value for branch - just leafs - this means that only the leafs are transformed...
  sealed trait Tree[+T]
  final case class Leaf[+T](value: T) extends Tree[T]
  final case class Branch[+T](left: Tree[T], right: Tree[T]) extends Tree[T]

  implicit object TreeMonad extends Monad[Tree] {
    override def pure[A](a: A): Tree[A] = Leaf(a)

    def flatMapStack[A, B](ta: Tree[A])(f: A => Tree[B]): Tree[B] = {
      ta match {
        case Leaf(value) => f(value)
        case Branch(left, right) => Branch(flatMap(left)(f), flatMap(right)(f))
      }
    }

    override def flatMap[A, B](ta: Tree[A])(f: A => Tree[B]): Tree[B] = {
      @tailrec
      def tailRec(todo: List[Tree[A]],
                  visited: Set[Tree[A]],
                  done: List[Tree[B]]): Tree[B] = {
        if (todo.isEmpty) done.head
        else todo.head match {
          case Leaf(a) =>
            tailRec(todo.tail, visited, f(a) :: done)
          case node @ Branch(left, right) =>
            if (!visited.contains(node)) {
              tailRec(left :: right :: todo, visited + node, done)
            } else {
              val newLeft = done.head
              val newRight = done.tail.head
              tailRec(
                todo.tail,
                visited,
                Branch(newLeft, newRight) :: done.drop(2)
              )
            }
        }
      }

      tailRec(List(ta), Set.empty, List.empty)
    }

    def tailStackRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] = {
      def stackRec(t: Tree[Either[A, B]]): Tree[B] = t match {
        case Leaf(Left(a)) => stackRec(f(a))
        case Leaf(Right(b)) => Leaf(b)
        case Branch(left, right) => Branch(stackRec(left), stackRec(right))
      }

      stackRec(f(a))
    }

    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] = {

      def tailRec(todo: List[Tree[Either[A, B]]],    // List of nodes not processed yet
                  expanded: Set[Tree[Either[A, B]]], // Set of nodes I have already processed
                  done: List[Tree[B]]): Tree[B] =    // Accumulator with the result (@ the end I will return done.head)
      {
        if (todo.isEmpty) done.head // stopping condition
        else todo.head match { // get next item of work from the todo list
          case Leaf(Left(a)) =>
            // keep iterating on the head of the todo list
            tailRec(f(a) :: todo.tail, expanded, done)

          case Leaf(Right(b)) =>
            // pop the head from the todo list and add it to the done list
            tailRec(todo.tail, expanded, Leaf(b) :: done)

          case node @ Branch(left, right) =>
            // if the node has not been expanded before, spit it into left/right nodes and add
            // them to the todo list
            if (!expanded.contains(node)) {
              tailRec(right :: left :: todo, expanded + node, done)
            } else {
              // We have visited this node before:
              // reconstruct a new node from whatever has been accumulated in the done list
              val newLeft = done.head
              val newRight = done.tail.head
              tailRec(
                todo.tail, // pop the current node from the todo list
                expanded,
                // replace 2 elements from the done list with the new reconstructed branch
                Branch(newLeft, newRight) :: done.drop(2))
            }
        }
      }

      tailRec(List(f(a)), Set(), List())
    }
  }

  val tree: Tree[Int] = Branch(Leaf(10), Leaf(20))
  val changedTree = TreeMonad.flatMap(tree)(v => Branch(Leaf(v + 1), Leaf(v + 2)))

  println("---------------[ OUTPUT 1]-----------")
  println(tree)
  println(changedTree)
  println("-------------------------------------")


}
