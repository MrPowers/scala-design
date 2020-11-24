package mrpowers.scala.design

import scala.io.Source

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class Chapter7Spec extends AnyFunSpec with Matchers {

  def myDivider(x: Int, y: Int): Either[String, Int] = {
    if (y == 0) Left("You can't divide by zero")
    else Right(x / y)
  }

  it("shows a simple Either, Left, Right usage pattern") {
    myDivider(5, 0) should be(Left("You can't divide by zero"))
    myDivider(10, 2) should be(Right(5))
  }

  def getContent(url: java.net.URL): Either[String, Source] =
    if (url.getHost.contains("google"))
      Left("Requested URL is blocked for the good of the people!")
    else
      Right(Source.fromURL(url))

  it("shows how to return Left and Right values") {
    getContent(new java.net.URL("http://danielwestheide.com")) shouldBe a [Right[_, _]]
    getContent(new java.net.URL("http://danielwestheide.com")).isRight should be(true)

    getContent(new java.net.URL("http://google.com")) shouldBe a [Left[_, _]]
    getContent(new java.net.URL("http://google.com")).isLeft should be(true)
  }

  it("shows how Either works well with pattern matching") {
    val res = getContent(new java.net.URL("http://google.com")) match {
      case Left(msg) => println(msg)
      case Right(source) => source.getLines.foreach(println)
    }
    println(res)
  }

  it("shows how to go right") {
    val content: Either[String, Iterator[String]] =
      getContent(new java.net.URL("http://danielwestheide.com")).right.map(_.getLines())
    // content is a Right containing the lines from the Source returned by getContent
    val moreContent: Either[String, Iterator[String]] =
      getContent(new java.net.URL("http://google.com")).right.map(_.getLines)
    // moreContent is a Left, as already returned by getContent
  }

  it("shows how to go left") {
    val content: Either[Iterator[String], Source] =
      getContent(new java.net.URL("http://danielwestheide.com")).left.map(Iterator(_))
    // content is the Right containing a Source, as already returned by getContent
    val moreContent: Either[Iterator[String], Source] =
      getContent(new java.net.URL("http://google.com")).left.map(Iterator(_))
    // moreContent is a Left containing the msg returned by getContent in an Iterator
  }

  def getProtocol(url: java.net.URL): Either[String, Source] =
    if (url.getProtocol == "http")
      Left("Please use https")
    else
      Right(Source.fromURL(url))

  it("returns a Left if the protocol is http") {
    val url = new java.net.URL("http://www.codequizzes.com")
    getProtocol(url) should be(Left("Please use https"))
  }

  it("returns a Right if the protocol is https") {
    val url = new java.net.URL("https://www.mungingdata.com")
    getProtocol(url) shouldBe a [Right[_, _]]
  }

  def coolDivide(x: Int, y: Int): Either[String, Int] = {
    if (y == 0) Left("You can't divide by zero silly")
    else Right(x / y)
  }

  it("returns a Left if the denominator is zero") {
    coolDivide(3, 0) should be(Left("You can't divide by zero silly"))
  }

  it("returns a Right if the division is OK") {
    coolDivide(15, 3) should be(Right(5))
  }

  describe("getOrElse") {
    it("shows how Left values work wth getOrElse") {
      Left("hi").getOrElse("whatever") should be("whatever")
    }

    it("shows how Right values work wth getOrElse") {
      Right("hi").getOrElse("whatever") should be("hi")
    }
  }

}
