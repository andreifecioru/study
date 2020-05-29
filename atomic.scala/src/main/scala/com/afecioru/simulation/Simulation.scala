package com.afecioru.simulation

class Simulation {

  type Action = () => Unit

  case class WorkItem(time: Int, action: Action)

  private var agenda: List[WorkItem] = Nil
  private var cTime: Int = _



  private def insertWorkItem(workItem: WorkItem, _agenda: List[WorkItem]): List[WorkItem] = {
    if (_agenda.isEmpty || workItem.time < _agenda.head.time) workItem :: _agenda
    else _agenda.head :: insertWorkItem(workItem, _agenda.tail)
  }

  private def next(): Unit = {
    (agenda: @unchecked) match {
      case workItem :: rest =>
        agenda = rest
        cTime = workItem.time
        print(s"[$cTime] > ")
        workItem.action()
    }
  }

  def currentTime: Int = cTime

  def afterDelay(delay: Int)(block: => Unit ): Unit = {
    val workItem = WorkItem(delay, () => block)
    agenda = insertWorkItem(workItem, agenda)
  }

  def run(): Unit = {
    afterDelay(0) {
      println("-------[ SIMULATION START ]----------")
    }
    while (agenda.nonEmpty) next();
  }
}
