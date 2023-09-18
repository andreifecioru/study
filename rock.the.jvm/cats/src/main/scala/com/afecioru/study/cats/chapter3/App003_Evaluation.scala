package com.afecioru.study.cats.chapter3

object App003_Evaluation extends App {

  /*
   In cats the evaluation abstraction encapsulates in a data container an expression that can
   be reduced to a value.

   There are 3 types of evaluation:
    - eager: like a val definition - evaluates once at definition site
    - always: like a def - evaluates with each invocation
    - later: like a lazy val - evaluates one at call-site (a.k.a. memoizing)
  */

  import cats.Eval

  val eagerEval: Eval[String] = Eval.now {
    println("Eager evaluation are computed once at the definition site")
    "Eval.now"
  }

  val alwaysEval: Eval[String] = Eval.always {
    println("Always evaluation behaves like a def")
    "Eval.always"
  }

  val laterEval: Eval[String] = Eval.later {
    println("Later evaluation behaves like a lazy val")
    "Eval.later"
  }

  // Eval containers can be combined in for-comp expression
  val result = for {
    now1 <- eagerEval
    now2 <- eagerEval
    always1 <- alwaysEval
    always2 <- alwaysEval
    later1 <- laterEval
    later2 <- laterEval
  } yield {
    (now1, now2, always1, always2, later1, later2)
  }

  println("---------[ OUTPUT 1]-----------")
  // we need to call `.value` to extract the result
  println(s"Result is: ${result.value}")
  println("-------------------------------")

  // An always eval can be instructed to memoize the value
  val recipe = Eval
    .always {
      println("Step 1")
      "Peel potatoes"
    }
    .map { step1 =>
      println("Step 2")
      s"$step1 and then boil water"
    }
    .memoize // remember everything computes so far - the first 2 steps will only be computed once
    .map { step2 => // step 3 will always be computed with each invocation
      println("Step 3")
      s"$step2 and then put potatoes in water."
    }

  println("---------[ OUTPUT 2]-----------")
  println(s"Recipe: ${recipe.value}")
  println(s"Recipe: ${recipe.value}")
  println(s"Recipe: ${recipe.value}")
  println("-------------------------------")


  // Exercise 1: defer w/o side-effects
  def defer[T](eval: => Eval[T]): Eval[T] = {
     Eval.later(()).flatMap(_ => eval)
  }

  println("---------[ OUTPUT 3]-----------")
  val deferred = defer(Eval.now {
    println("now")
    42
  })
  println(s"Deferred: ${deferred.value}")
  println("-------------------------------")

  // Exercise 2: reverse a list using Eval
  def reverseList[T](list: List[T]): Eval[List[T]] = {
    if (list.isEmpty) Eval.now(list)
    else {
      // in order to avoid stack-overflow errors wrap the recursive call in "defer"
      Eval.defer(reverseList(list.tail).map { tail => tail :+ list.head })
    }
  }

  val nums = List(1, 2, 3, 4, 5)
  println("---------[ OUTPUT 4]-----------")
  println(reverseList(nums).value)
  println("-------------------------------")
}
