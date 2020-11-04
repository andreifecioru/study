package com.afecioru.apps.enumerations


object Colors extends Enumeration {
  val Red, Green, Black = Value
}

object WeekDays extends Enumeration {
  val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
}

object ShowFunctions {
  def show(color: Colors.Value): Unit = {
    // the compiler does not complain that the match is not exhaustive
    color match {
      case Colors.Red => println("This is red.")
      case Colors.Green => println("This is green")
    }
  }

  // Trying to overload based on enumeration type is not possible
  // because the enumeration type dissapears due to type-erasure.
  // def show(color: WeekDays.Value): Unit = { println("This is a weekday...") }
}

// The base enum type
trait Enum {
  import java.util.concurrent.atomic.AtomicReference;

  // This type must exist in the implementing class
  type EnumVal <: Value

  // Container for our enum values (thread-safe)
  private val _values = new AtomicReference(Vector[EnumVal]())

  // Handles the thread-safe addition of new enum-values.
  // Returns the index in the container of enum vals (used for ordering)
  private final def addEnumVal(newVal: EnumVal): Int = {
    val oldVec = _values.get()
    val newVec = oldVec :+ newVal
    if ((_values.get() eq oldVec) && _values.compareAndSet(oldVec, newVec))
      newVec.indexWhere(_ eq newVal)
    else
      addEnumVal(newVal) // try again
  }

  // Provide access to the collection of enumeration values
  def values: Vector[EnumVal] = _values.get()

  // The EnumVal type must extend this trait
  protected trait Value extends Ordered[Value] { self: EnumVal => // this trait can only be mixed into EnumVal types
    final val ordinal: Int = addEnumVal(this)

    override def compare(that: Value): Int = this.ordinal - that.ordinal;

    def name: String // enum values should have a name

    override def toString(): String = name
    override def equals(other: Any): Boolean = this eq other.asInstanceOf[AnyRef]
    override def hashCode(): Int = 31 * (this.getClass().hashCode() + name.hashCode() + ordinal)
  }
}

// Attempt ar replicating the 'Planets' enumeration on the official Java doc
// pages about `enum`s
object Planets extends Enum {
  // type aliases
  type Kilogram = Double
  type Meter = Double

  // constant definitions
  private val G = 6.67300E-11

  sealed abstract class EnumVal(mass: Kilogram, radius: Meter) extends Value {
    lazy val surfaceGravity: Double = G * mass / (radius * radius)
    def surfaceWeight(otherMass: Kilogram): Kilogram = surfaceGravity * otherMass
  }

  // this is basically our enumeration
  val MERCURY = new EnumVal(3.303e+23, 2.4397e6) { val name = "Mercury" }
  val VENUS   = new EnumVal(4.869e+24, 6.0518e6) { val name = "Venus"   }
  val EARTH   = new EnumVal(5.976e+24, 6.37814e6){ val name = "Earth"   }
  val MARS    = new EnumVal(6.421e+23, 3.3972e6) { val name = "Mars"    }
  val JUPITER = new EnumVal(1.9e+27,   7.1492e7) { val name = "Jupiter" }
  val SATURN  = new EnumVal(5.688e+26, 6.0268e7) { val name = "Saturn"  }
  val URANUS  = new EnumVal(8.686e+25, 2.5559e7) { val name = "Uranus"  }
  val NEPTUNE = new EnumVal(1.024e+26, 2.4746e7) { val name = "Neptune" }
}


object MainApp extends App {
  // We have ordering between enumeration values
  println(s"Is 'Red' less-than 'Black'? ${Colors.Red < Colors.Black}")


  // This will crash with a MatchError at run-time
  // ShowFunctions.show(Colors.Black)

  // We can enumerate the values in our enumeration
  println(Planets.values)

  // Exhaustion checks on pattern mathcing
  val planet: Planets.EnumVal = Planets.EARTH;
  planet match {
    case Planets.EARTH => println("This is Earth...")
    case Planets.MARS => println("This is Mars...")
    case _ => println("This is some other planet...")
  }

  // Ordering
  println(Planets.NEPTUNE > Planets.MERCURY) // true
  println(Planets.NEPTUNE < Planets.MERCURY) // false
}