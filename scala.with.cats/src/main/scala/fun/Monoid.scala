package fun

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit monoid: Monoid[A]): Monoid[A] = monoid

  def empty[A](implicit monoid: Monoid[A]): A = monoid.empty
  def combine[A](op1: A, op2: A)(implicit monoid: Monoid[A]): A = monoid.combine(op1, op2)
}


object MonoidInstances {
  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    override def empty: Int = 0
    override def combine(op1: Int, op2: Int): Int = SemigroupInstances.intSemigroup.combine(op1, op2)
  }

  implicit def listMonoid[A]: Monoid[List[A]] = new Monoid[List[A]] {
    override def empty: List[A] = List.empty
    override def combine(op1: List[A], op2: List[A]): List[A] = SemigroupInstances.listSemigroup.combine(op1, op2)
  }
}
