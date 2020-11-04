package com.afecioru.apps.either




object MainApp extends App {
  def getStringOrInt(in: Any): Either[String, Int] = {
    in match {
      case v: String => Left(v.toUpperCase)
      case v: Int => Right(v + 1)
      case _ => Left(in.toString)
    }
  }

  println(getStringOrInt(30).left)

  // Either is right-biased: we need to explicitly do a "left-projection"
  // to access the string.
  val result1 = for (
    name <- getStringOrInt("Andrei").left;
    age <- getStringOrInt(40)
  ) yield {
    s"Name: $name. Age: $age"
  }
  println(s"Result: $result1")

  // The for comp will stop at the first instance where the Either is missing
  // the expected projection. The result will be the expression that was responsible for
  // stopping the for-comp
  val result2 = for (
    firstName <- getStringOrInt("Andrei").left;
    age <- getStringOrInt(40).left; // here, we expected "left" but we got "right" instead.
    lastName <- getStringOrInt("Fecioru").left
  ) yield {
    s"Name: $firstName $lastName. Age: $age"
  }
  println(s"Result: $result2") // Right(41)

  val result3 = for (
    age <- getStringOrInt(40);
    firstName <- getStringOrInt("Andrei").left;
    lastName <- getStringOrInt("Fecioru").left
  ) yield {
    s"Name: $firstName $lastName. Age: $age"
  }
  println(s"Result: $result3") 
}