package com.afecioru.ch03.exercises

case class Message(id: Int, parent: Option[Int], msg: String) {
  override def toString(): String = s"#$id $msg"
}

object ThreadedMessages {
  def processMessages(msgs: Seq[Message]): Seq[(Int, Message)] = {
    msgs.map((0, _))
  }


  def print(msgs: Seq[Message]): Unit = {
    processMessages(msgs).foreach {
      case (indentLvl, msg) => println(s"${" " * indentLvl}$msg")
    }
  }

}


