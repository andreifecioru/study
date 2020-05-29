package com.afecioru.apps

class Email(userName: String, domain: String) {
  override def toString: String = s"User: $userName | Domain: $domain"
}

object Email {
  def apply(userName: String, domain: String): Email = new Email(userName, domain)

  def unapply(email: String): Option[(String, String)] = {
    val tokens = email.split("@").toList
    tokens match {
      case userName :: domain :: Nil => Some(userName -> domain)
      case _ => None
    }
  }
}

object DotComDomain {
  def unapply(domain: String): Boolean = domain.endsWith(".com")

}

object Domain {
  // support for varargs
  def unapplySeq(domain: String): Option[Seq[String]] = Some(domain.split("\\.").toSeq)
}

object ExtractorsApp extends App {
  val emailString = "andrei.fecioru@gmail.com"

  emailString match {
    // nested pattern matching
    case Email(userName, DotComDomain()) =>
      println("We are on a .com domain")

    case Email(userName, Domain(provider, _*)) =>
      println(s"Provider: $provider")

    // in the context of a pattern match this construct
    // calls the `unapply` (not the `apply`) method
    case Email(userName, domain) =>
      println(Email(userName, domain))

    case _ =>
      println("Not an e-mail.")
  }
}
