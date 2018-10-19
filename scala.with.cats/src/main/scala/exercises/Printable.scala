package exercises

// Type class: we want to "process" various types (producing an Int)
trait Printable[A] {
    def format(value: A): String
}

object Printable {

    // interfaces
    def format[A](value: A)(implicit printable: Printable[A]): String = {
        decorate(printable.format(value))
    }

    def print[A](value: A)(implicit printable: Printable[A]): Unit = {
        println(format(value))
    }

    private def decorate(in: String): String = s">> $in"
}

object PrintableInstances {
    // instances
    implicit val intPrintable: Printable[Int] = new Printable[Int] {
        def format(value: Int): String = value.toString
    }

    implicit val boolPrintable: Printable[Boolean] = new Printable[Boolean] {
        def format(value: Boolean): String = value.toString
    }

    // recursive implicit resolution
    implicit def optionPrintable[A](implicit printable: Printable[A]): Printable[Option[A]] = {
        new Printable[Option[A]] {
            override def format(value: Option[A]): String = value match {
                case Some(v) => s"Some(${printable.format(v)})"
                case None => "None"
            }
        }
    }

    implicit def seqPrintable[A](implicit  printable: Printable[A]): Printable[Seq[A]] = {
        new Printable[Seq[A]] {
            override def format(value: Seq[A]): String = value.foldLeft("")((acc, v) => {
                acc match {
                    case "" => printable.format(v)
                    case _ => s"$acc:${printable.format(v)}"
                }
            })
        }
    }
}

object PrintableSyntax {
    implicit class PrintableOps[A](value: A) {
        def format(implicit printable: Printable[A]): String = {
            printable.format(value)
        }

        def print(implicit printable: Printable[A]): Unit = {
            Printable.print(value)
        }
    }
}

