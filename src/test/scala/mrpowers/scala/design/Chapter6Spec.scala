package mrpowers.scala.design

import org.scalatest.{FunSpec, Matchers}

import scala.util.{Failure, Success}

class Chapter6Spec extends FunSpec with Matchers {

  case class Customer(age: Int)
  class Cigarettes
  case class UnderAgeException(message: String) extends Exception(message)
  def buyCigarettes(customer: Customer): Cigarettes =
    if (customer.age < 16)
      throw UnderAgeException(s"Customer must be older than 16 but was ${customer.age}")
    else new Cigarettes

  // this type of exception handling is ugly and doesn't go well with functional programming
  it("shows the non-functional way of dealing with exceptions") {
    val youngCustomer = Customer(15)
    val res = try {
      buyCigarettes(youngCustomer)
      "Yo, here are your cancer sticks! Happy smokin'!"
    } catch {
      case UnderAgeException(msg) => msg
    }
    res should be("Customer must be older than 16 but was 15")
  }

  it("fetches a URL") {
    import scala.util.Try
    import java.net.URL
    def parseURL(url: String): Try[URL] = Try(new URL(url))

    parseURL("https://mungingdata.com/") shouldBe a [Success[_]]
    parseURL("this IS NOT a URL") shouldBe a [Failure[_]]
  }

}
