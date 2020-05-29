package com.afecioru.simulation

object CircuitSimulationApp extends App {

  object SimulationInstance extends CircuitSimulation {
    override def InverterDelay: Int = 1
    override def AndGateDelay: Int = 3
    override def OrGateDelay: Int = 5
  }

  import SimulationInstance._

  val in1, in2, sum, carry = new Wire

  // install probes
  probe("sum", sum)
  probe("carry", carry)

  // install the circuit
  halfAdder(in1, in2, sum, carry)

  // set input signals
  in1.signal = true
  in2.signal = true

  // run the simulation
  run()


}
