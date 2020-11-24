package mrpowers.scala.design

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import scala.util.{Failure, Success}
import scala.util.Try
import java.net.URL

class Chapter6Spec extends AnyFunSpec with Matchers {

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
    def parseURL(url: String): Try[URL] = Try(new URL(url))
    parseURL("https://mungingdata.com/") shouldBe a [Success[_]]
    parseURL("this IS NOT a URL") shouldBe a [Failure[_]]
  }

  it("demonstrates how to use isSuccess") {
    val url = Try(new URL("https://www.blah.com"))
    val res = if (url.isSuccess) "we have a URL" else "where to go?"
    res should be("we have a URL")
  }

  it("shows how to use getOrElse") {
    val url = Try(new java.net.URL("blah"))
    val res = url.getOrElse("I am broken")
    res should be("I am broken")
  }

  it("shows how map can return Failure values") {
    val url = Try(new URL("blah"))
    val res = url.map(_.getProtocol)
    res shouldBe a[Failure[java.net.MalformedURLException]]
  }

  it("shows how map can return Success values") {
    val url = Try(new URL("https://www.mungingdata.com"))
    val res = url.map(_.getProtocol)
    res should be(Success("https"))
  }

  // skipped some more stuff

}
