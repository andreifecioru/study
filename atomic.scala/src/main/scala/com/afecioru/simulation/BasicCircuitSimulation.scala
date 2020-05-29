package com.afecioru.simulation

abstract class BasicCircuitSimulation extends Simulation {
  def InverterDelay: Int
  def AndGateDelay: Int
  def OrGateDelay: Int

  class Wire {
    private var sigValue: Boolean = false
    private var actions: List[Action] = List()

    def signal: Boolean = sigValue
    def signal_=(s: Boolean): Unit = {
      if (s != sigValue) {
        sigValue = s  // update the signal value
        actions.foreach(_()) // execute associated actions
      }
    }

    def addAction(action: Action): Unit = {
      actions = action :: actions
      action()
    }
  }

  def inverter(input: Wire, output: Wire): Unit = {
    def invertAction(): Unit = {
      val inSig = input.signal // read the inputs

      afterDelay(InverterDelay) { // set the output
        output.signal = !inSig
      }
    }

    // install input wire actions
    input.addAction(invertAction)
  }

  def andGate(in1: Wire, in2: Wire, out: Wire): Unit = {
    def andAction(): Unit = {
      val in1Sig = in1.signal // read the inputs
      val in2Sig = in2.signal

      afterDelay(AndGateDelay) { // set the outputs
        out.signal = (in1Sig & in2Sig )
      }
    }

    // install input wire actions
    in1.addAction(andAction)
    in2.addAction(andAction)
  }

  def orGate(in1: Wire, in2: Wire, out: Wire): Unit = {
    def orAction(): Unit = {
      val in1Sig = in1.signal // read the inputs
      val in2Sig = in2.signal

      afterDelay(AndGateDelay) { // set the outputs
        out.signal = (in1Sig | in2Sig )
      }
    }

    // install input wire actions
    in1.addAction(orAction)
    in2.addAction(orAction)
  }

  def probe(name: String, wire: Wire): Unit = {
    wire.addAction {() =>
      println(s"($name): ${wire.signal}")
    }
  }

}
