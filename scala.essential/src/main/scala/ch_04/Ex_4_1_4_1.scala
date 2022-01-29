package org.afecioru.study
package ch_04

trait Feline {
  def name: String
  def colour: String
  def sound: String
}

case class Cat(name: String, colour: String) extends Feline {
  val sound = "meow"
  val food = "fish"
}

case class Tiger(name: String, colour: String) extends Feline {
  val sound = "roar"
}

case class Panther(name: String, colour: String) extends Feline {
  val sound = "roar"
}

case class Lion(name: String, colour: String) extends Feline {
  val sound = "roar"
}

class Ex_4_1_4_1 extends BaseTestSuite {

}
