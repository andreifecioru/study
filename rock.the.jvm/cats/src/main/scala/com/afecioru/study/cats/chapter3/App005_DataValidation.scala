package com.afecioru.study.cats.chapter3

import scala.annotation.tailrec
import scala.util.Try

/*
  Cats provides the Validated[L, R] container which captures whether
  the data contained within is valid or not.

  It acts like an Either, and has 2 type params:
    - L - the "undesirable" type => represents data that is not valid
    - R - the "desirable" type => represents data that is valid
*/

object App005_DataValidation extends App {

  import cats.data.Validated

  println("-----------[ OUTPUT 1 ]------------")
  val aValidValue: Validated[String, Int] = Validated.valid(42) // "right" value
  val anInvalidValue: Validated[String, Int] = Validated.invalid("Error") // "left" value

  // "cond" API produces a Validated instance based on:
  //   - a predicate,
  //   - a "left" value
  //   - a "right" value
  val aTest: Validated[String, Int] = Validated.cond(
    10 > 11, // the predicate
    1, // the "right" value
    "wrong" // the "left" value
  )
  println(aTest)

  /* Exercise 1: implement multiple validation criteria using Either,
                 while accumulating all invalid messages

       The input no must:
        - be even
        - be <= 100
        - be prime
        - be >= 0 (positive)
  */
  def testNumber(num: Int): Either[List[String], Int] = {
    def isPrime(n: Int): Either[String, Int] = {
      @tailrec
      def innerLoop(divisor: Int): Either[String, Int] =
        if (divisor >= n) Right(n)
        else {
          if (n % divisor == 0) Left(s"$n is divisible with $divisor")
          else innerLoop(divisor + 1)
        }

      if (num == 0 || num == 1 || num == -1)
        Left(s"$num is not prime or non-prime")
      else
        innerLoop(2)
    }

    def isEven(n: Int): Either[String, Int] =
      if (n % 2 == 0) Right(n)
      else Left(s"$n is not an even number")

    def isSmall(n: Int): Either[String, Int] =
      if (n <= 100) Right(n)
      else Left(s"$n is bigger than 100")

    def isPositive(n: Int): Either[String, Int] =
      if (n >= 0) Right(n)
      else Left(s"$n is not a positive number")

    Seq(
      isEven(num),
      isSmall(num),
      isPrime(Math.abs(num / 2)),
      isPositive(num)
    ).foldLeft(Right(num): Either[List[String], Int]) { (acc, item) =>
      item match {
        case Right(_) =>
          acc
        case Left(err) =>
          Left(acc.left.toOption.map(_ :+ err).getOrElse(List(err)))
      }
    }
  }

  println("-----------[ OUTPUT 2 ]------------")
  println(s"Testing 10: ${testNumber(10)}")
  println(s"Testing 1000: ${testNumber(1000)}")
  println(s"Testing -123: ${testNumber(-123)}")
  println(s"Testing 2: ${testNumber(2)}")

  def testNumber2(num: Int): Either[List[String], Int] = {
    lazy val isPrime: List[String] = {
      @tailrec
      def innerLoop(divisor: Int): List[String] =
        if (divisor >= num) List.empty
        else {
          if (num % divisor == 0) List(s"$num is divisible with $divisor")
          else innerLoop(divisor + 1)
        }

      if (num == 0 || num == 1 || num == -1)
        List(s"$num is not prime or non-prime")
      else
        innerLoop(2)
    }

    lazy val isEven: List[String] =
      if (num % 2 == 0) List.empty
      else List(s"$num is not an even number")

    def isSmall: List[String] =
      if (num <= 100) List.empty
      else List(s"$num is bigger than 100")

    def isPositive: List[String] =
      if (num >= 0) List.empty
      else List(s"$num is not a positive number")

    isPrime ++ isEven ++ isSmall ++ isPositive match {
      case Nil => Right(num)
      case errs => Left(errs)
    }
  }

  println("-----------[ OUTPUT 3 ]------------")
  println(s"Testing 10: ${testNumber2(10)}")
  println(s"Testing 1000: ${testNumber2(1000)}")
  println(s"Testing -123: ${testNumber2(-123)}")
  println(s"Testing 2: ${testNumber2(2)}")

  // Validated is better than Either because it can be composed and we can accumulate the
  // multiple errors (i.e. undesirable "left" values) as we encounter them

  def validateNumber(num: Int): Validated[List[String], Int] = {

    import cats.Semigroup
    import cats.instances.list._ // fetch a Semigroup[List] to combine the errors

    def checkPrime(n: Int): Boolean = {
      @tailrec
      def innerLoop(divisor: Int): Boolean =
        if (divisor >= n) true
        else {
          if (num % divisor == 0) false
          else innerLoop(divisor + 1)
        }

      innerLoop(2)
    }

    val notPrimeOrNonPrime = Validated.cond(num != 0 && num != -1 && num != 1, num, List(s"$num is neither prime or non-prime"))
    val isEven = Validated.cond(num % 2 == 0, num, List(s"$num is not an even number"))
    val isSmall = Validated.cond(num <= 100, num, List(s"$num is bigger than 100"))
    val isPositive = Validated.cond(num > 0, num, List(s"$num is not a positive number"))
    val isPrime = Validated.cond(checkPrime(num), num, List(s"$num is not a prime number"))

    // NOTE: both the values and errors get combined
    // we need to define a custom Semigroup[Int] to properly combine the values (by default they get added)
    implicit val valuesCombinator: Semigroup[Int] = Semigroup.instance[Int] { (v1, _) => v1 }
    Seq(notPrimeOrNonPrime, isEven, isPositive, isSmall, isPrime).reduce(_ combine _)
  }

  println("-----------[ OUTPUT 4 ]------------")
  println(s"Testing 10: ${validateNumber(10)}")
  println(s"Testing 1000: ${validateNumber(1000)}")
  println(s"Testing -123: ${validateNumber(-123)}")
  println(s"Testing 2: ${validateNumber(2)}")

  // Chaining validations
  // looks like a flatMap but it is not, because flatMap would stop at the first
  // invalid value, and errors would stop accumulating
  aValidValue.andThen(_ => anInvalidValue)

  // test a value inside a Validated using a predicate
  aValidValue.ensure(List("not even number"))(_ % 2 == 0)

  // Transformations (map)
  aValidValue.map(_ + 1) // transform the value
  aValidValue.leftMap(_.length) // transform the error
  aValidValue.bimap(_.length, _ + 1) // transform both value and error

  // Interop. with classes in stdlib
  val eitherToValidated: Validated[List[String], Int] = Validated.fromEither(Right(42))
  val optionToValidated: Validated[List[String], Int] = Validated.fromOption(None, List("option was empty"))
  val tryToValidated: Validated[Throwable, Int] = Validated.fromTry(Try("not a number".toInt))

  val validatedToOption: Option[Int] = aValidValue.toOption
  val validateToEither: Either[String, Int] = anInvalidValue.toEither

  /* Exercise 2: user registration form validation

    The fields in the form are:
      - name
      - email
      - password

    Validation rules:
      - name, email and passwd must pe specified
      - they must not be blank
      - email must contain '@'
      - password length exceeds 10 characters

    If validation is successful return "Success".
   */

  type Form = Map[String, String]
  type FormValidation[T] = Validated[List[String], T]

  def validateForm(form: Form): FormValidation[String] = {
    val success = "Success"
    val validForm: FormValidation[String] = Validated.valid(success)

    def fieldIsPresent(field: String): FormValidation[String] = {
      val fieldIsSpecified: FormValidation[String] = {
        Validated.fromOption(form.get(field), List(s"$field must be specified"))
      }

      fieldIsSpecified.andThen { value =>
        Validated.cond(value.nonEmpty, success, List(s"$field cannot be blank"))
      }
    }

    def fieldsArePresent(fields: Seq[String]): FormValidation[String] = {
      fields.foldLeft(validForm)(_ combine fieldIsPresent(_))
    }

    val emailIsValid: FormValidation[String] =
      Validated.cond(
        form.getOrElse("email", "").contains("@"),
        success,
        List(s"email must contain '@'")
      )

    val passwordIsValid: FormValidation[String] =
      Validated.cond(
        form.getOrElse("password", "").length >= 10,
        success,
        List(s"password too short")
      )

    Seq(
      fieldsArePresent(Seq("name", "email", "password")),
      emailIsValid,
      passwordIsValid
    ).reduce(_ combine _).map(_ => success)

  }

  println("-----------[ OUTPUT 5 ]------------")
  println(validateForm(Map()))
  println(validateForm(Map("name" -> "Andrei")))
  println(validateForm(Map(
    "name" -> "Andrei",
    "email" -> "andrei@email.com",
    "password" -> "adsjfdlnvlslasnfn",
  )))

  // syntactic sugar extension methods
  import cats.syntax.validated._
  val aValidInt: Validated[List[String], Int] = 42.valid
  val anError: Validated[String, Int] = "Error".invalid
}
