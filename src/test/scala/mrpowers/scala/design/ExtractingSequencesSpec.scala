package mrpowers.scala.design

import org.scalatest.{FunSpec, Matchers}

class ExtractingSequencesSpec extends FunSpec with Matchers {

  object Names {
    def unapplySeq(name: String): Option[(String, String, Seq[String])] = {
      val names = name.trim.split(" ")
      if (names.size < 2) None
      else Some((names.last, names.head, names.drop(1).dropRight(1)))
    }
  }

  def greet(fullName: String) = fullName match {
    case Names(lastName, firstName, _*) => "Good morning, " + firstName + " " + lastName + "!"
    case _ => "Welcome! Please make sure to fill in your name!"
  }

  it("greets differently") {
    greet("Daniel") should be("Welcome! Please make sure to fill in your name!")
    greet("Catherina Johanna") should be("Good morning, Catherina Johanna!")
    greet("") should be("Welcome! Please make sure to fill in your name!")
  }

}
