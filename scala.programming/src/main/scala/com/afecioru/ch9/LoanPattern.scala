package main.scala.com.afecioru.ch9

import java.io.{File, PrintWriter}

object LoanPattern extends App {
  def withPrintWriter(file: File)(op: PrintWriter => Unit): Unit = {
    val printWriter = new PrintWriter(file)
    try {
      op(printWriter)
    } finally {
      printWriter.close()
    }
  }

  withPrintWriter(new File("data.txt")) { writer =>
    writer.println("Andrei is the best!")
  }
}
