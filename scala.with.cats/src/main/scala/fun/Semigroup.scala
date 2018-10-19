package fun

trait Semigroup[A] {
  def combine(op1: A, op2: A): A
}

object Semigroup {
  def apply[A](implicit semigroup: Semigroup[A]): Semigroup[A] = semigroup

  def combine[A](op1: A, op2: A)(implicit semigroup: Semigroup[A]): A =
    semigroup.combine(op1, op2)
}

object SemigroupInstances {
  implicit val intSemigroup: Semigroup[Int] = new Semigroup[Int] {
    override def combine(op1: Int, op2: Int): Int = op1 + op2
  }

  implicit def listSemigroup[A]: Semigroup[List[A]] = new Semigroup[List[A]] {
    override def combine(op1: List[A], op2: List[A]): List[A] = op1 ++ op2
  }
}

