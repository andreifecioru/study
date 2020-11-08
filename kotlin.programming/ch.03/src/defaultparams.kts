// default arguments can be expressions computed based on the values of params to its left
fun printMsg(msg: String, suffix: String = "len: ${msg.length}") = println("$msg ($suffix)")

printMsg("Hello, Andrei")
printMsg("Hello, there", suffix = "n/a")