package com.afecioru.simulation

object SimulationApp extends App {
  val sim = new Simulation

  sim.afterDelay(1) {
    println("Step 1")
  }

  sim.afterDelay(3) {
    println("Step 2")
  }

  sim.run()
}
