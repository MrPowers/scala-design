package mrpowers.scala.design

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class GivenNamesSpec extends AnyFunSpec with Matchers {

  def greetWithFirstName(name: String) = name match {
    case GivenNames(firstName, _*) => "Good morning, " + firstName + "!"
    case _ => "Welcome! Please make sure to fill in your name!"
  }

  it("patern matches the first name") {
    greetWithFirstName("Daniel") should be("Good morning, Daniel!")
    greetWithFirstName("Catherina Johanna") should be("Good morning, Catherina!")
    greetWithFirstName("") should be("Welcome! Please make sure to fill in your name!")
  }

}
