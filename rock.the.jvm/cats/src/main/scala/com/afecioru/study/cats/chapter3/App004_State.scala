package com.afecioru.study.cats.chapter3

object App004_State extends App {

  /*
    State is an abstraction which captures the idea of a system that is changing after a computation.
    We have an initial state S1 and we apply a computation F which when applied to the system produces a
    result R and changes the system to a new state S2.

    We want to be able to "chain" multiple such transformations in a pure FP way.
  */

  import cats.data.State

  // State has 2 type params: the state S and the result of the computation R
  // In the example below, the state S is an Int and the result R is a String
  val firstComputation: State[Int, String] = State { s: Int => // we provide the computation which inputs the old state
                                                               // and returns a tuple of the new state and the result
                                                               // (s1: S) => (s2, R)
    s + 1 -> s"Added 1 to $s and obtained ${s + 1}"
  }

  val secondComputation: State[Int, String] = State { s: Int =>
    s * 5 -> s"Multiplied by 5 and obtained ${s * 5}"
  }

  // This is basically function composition but it allows for monadic invocation chaining
  val computationResult1 = firstComputation.flatMap(r1 => // r1, r2 are the results of the computation
    secondComputation.map(r2 => (r1, r2))
  )

  // We can do for-comps
  val computationResult2 = for {
    r1 <- firstComputation
    r2 <- secondComputation
  } yield r1 -> r2

  println("----------[ OUTPUT 1 ]------------")
  // NOTE: to extract the final state and result, call `.run(<initial state>)` (which returns an `Eval`) and then `.state`
  println(s"Comp. result 1: ${computationResult1.run(10).value}")
  println(s"Comp. result 2: ${computationResult2.run(10).value}")
  println("----------------------------------")

  // Exercise 1: model an online store shopping cart using State
  final case class ShoppingCart(items: List[String], total: Double)
  object ShoppingCart {
    val empty: ShoppingCart = ShoppingCart(List(), 0)
  }

  def addToCart(item: String, price: Double): State[ShoppingCart, Double] = {
    State { cart =>
      val newTotal = cart.total + price
      (
        cart.copy(items = item :: cart.items, total = newTotal),
        newTotal
      )
    }
  }

  val checkout = for {
    _ <- addToCart("Books", 100)
    _ <- addToCart("Shirt", 50)
    r <- addToCart("Tools", 150)
  } yield r


  println("----------[ OUTPUT 2 ]------------")
  println(s"Shopping cart at checkout: ${checkout.run(ShoppingCart.empty).value._1}")
  println("----------------------------------")

  // Exercise 2: using State in various scenarios
  // Model an idempotent computation (state does not change)
  def inspect[S, R](f: S => R): State[S, R] = State(s => (s, f(s)))

  // Model getter/setter
  def get[S]: State[S, S] = State(s => (s, s))
  def mutate[S](f: S => S): State[S, Unit] = State(s => (f(s), ()))
  def set[S](newState: S): State[S, Unit] = State(_ => (newState, ()))
  def set1[S](newState: S): State[S, Unit] = mutate(identity)

  println("----------[ OUTPUT 3 ]------------")
  println(s"Get: ${get[String].run("Andrei").value}")
  println(s"Get: ${inspect[String, Int](s => s.length).run("Andrei").value}")
  println(s"Get: ${mutate[String](s => s.toUpperCase).run("Andrei").value}")
  println(s"Set: ${set[String]("Fecioru").run("Andrei").value}")
  println(s"Set: ${set1[String]("Fecioru").run("Andrei").value}")
  println("----------------------------------")

  // The methods above are already available in Cats on the State companion object
  // and we can define "programs" as sequences of operations applied to a system with state S
  import cats.data.State._
  val program = for {
    a <- State.get[Int]
    _ <- State.set[Int](a + 10)
    b <- State.get[Int]
    _ <- State.modify[Int](_ + 43)
    c <- State.inspect[Int, Int](_ * 2)
  } yield (a, b , c)


  println("----------[ OUTPUT 4 ]------------")
  println(s"Program output: ${program.run(10).value}")
  println("----------------------------------")
}
