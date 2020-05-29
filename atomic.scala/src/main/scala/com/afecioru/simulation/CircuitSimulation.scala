package com.afecioru.simulation

abstract class CircuitSimulation extends BasicCircuitSimulation {
  def halfAdder(in1: Wire, in2: Wire, sum: Wire, carry: Wire): Unit = {
    val t1, t2 = new Wire
    orGate(in1, in2, t1)
    andGate(in1, in2, carry)
    inverter(carry, t2)
    andGate(t1, t2, sum)
  }

}
