package mrpowers.scala.design.hands_on

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class HandsChapter3Spec extends AnyFunSpec with Matchers {

  def fizzBuzz(i: Int): Any = {
    val res = if (i % 3 == 0 && i % 5 == 0) "FizzBuzz"
    else if (i % 3 == 0) "Fizz"
    else if (i % 5 == 0) "Buzz"
    else i
    res
  }

  it("shows how to use the fizzBuzz function") {
    fizzBuzz(15) should be("FizzBuzz")
    fizzBuzz(2) should be(2)
    fizzBuzz(5) should be("Buzz")
    fizzBuzz(6) should be("Fizz")
  }

  describe("tuples") {

    it("shows how to create a tuple") {
      val t = (1, true, "hello")
      t._1 should be(1)
      t._2 should be(true)
      t._3 should be("hello")
    }

    it("shows how to create a type annotated tuple") {
      val t: (String, String) = ("Some", "Thing")
      t._1 should be("Some")
      t._2 should be("Thing")
    }

    it("shows how to extract values from a tuple") {
      val t: (String, String) = ("range rover", "surfing")
      val (myRide, myFun) = t
      myRide should be("range rover")
      myFun should be("surfing")
    }

  }

  describe("arrays") {

    it("shows how to fetch values from an array") {
      val a = Array[Int](1, 2, 3, 4)
      a(0) should be(1)
      a(3) should be(4)
      a(1) should be(2)
    }

    it("shows the mutable nature of arrays") {
      val a = new Array[Int](4)
      // default value for Int is 0
      a should equal(Array[Int](0, 0, 0, 0))
      a(0) = 1
      a(2) = 100
      a should equal(Array[Int](1, 0, 100, 0))
    }

    it("shows how to create nested arrays") {
      val multi = Array(Array(1, 2), Array(3, 4))
      multi(0)(0) should be(1)
      multi(1)(1) should be(4)
    }

  }

  describe("Options") {

    it("shows basic pattern matching") {
      def hello(title: String, firstName: String, lastName: Option[String]) = {
        lastName match {
          case Some(lastName) => s"Hello $title. $lastName"
          case None => s"Hello $firstName"
        }
      }
      hello("Mr", "James", None) should be("Hello James")
      hello("Mr", "James", Some("Bond")) should be("Hello Mr. Bond")
    }

    it("shows how to use getOrElse") {
      Some("bob").getOrElse("unknown") should be("bob")
      None.getOrElse("unknown") should be("unknown")
    }

  }

  describe("for loops") {
    it("sums items in an array") {
      var total = 0
      val items = Array(1, 10, 100, 1000)
      for (item <- items) total += item
      total should be(1111)
    }

    it("sums from 0 to 4") {
      var total = 0
      for (i <- Range(0, 5)) {
        total = total + i
      }
      total should be(10)
    }

    it("loops over a nested array") {
      val multi = Array(Array(1, 2), Array(3, 4), Array(5, 6))
      var total = 0
      for (arr <- multi; i <- arr) total = total + i
      total should be(21)
    }

    it("sums the even numbers in the nested array") {
      val multi = Array(Array(1, 2), Array(3, 4), Array(5, 6))
      var total = 0
      for (arr <- multi; i <- arr; if i % 2 == 0) total = total + i
      total should be(12)
    }
  }

  describe("comprehensions") {

    // comprehensions use for and yield to create new collections
    it("squares all the elements in a list") {
      val a = Array(1, 2, 3, 4)
      val a2 = for (i <- a) yield i * i
      a2 should equal(Array(1, 4, 9, 16))
    }

    it("creates an array of strings") {
      val a = Array(1, 2, 3, 4)
      val a2: Array[String] = for (i <- a) yield "hi " + i
      a2 should equal(Array("hi 1", "hi 2", "hi 3", "hi 4"))
    }

    it("works with an if guard") {
      val a = Array(1, 2, 3, 4)
      val a2 = for (i <- a if i % 2 == 0) yield "hi " + i
      a2 should equal(Array("hi 2", "hi 4"))
    }

    it("shows how comprehensions can take multiple input arrays") {
      val a = Array(1, 2)
      val b = Array("hello", "world")
      val flattened = for (i <- a; s <- b) yield s + i
      flattened should equal(Array("hello1", "world1", "hello2", "world2"))
    }

    it("shows how the for arguments can be split on multiple lines") {
      val a = Array(1, 2)
      val b = Array("hello", "world")
      val flattened = for {
        i <- a
        s <- b
      } yield s + i
      flattened should equal(Array("hello1", "world1", "hello2", "world2"))
    }

  }

  describe("methods") {
    // methods are created with the def keyword

    it("creates a simple method") {
      def fullName(firstName: String, lastName: String): String =
        firstName + " " + lastName
      fullName("Matthew", "Powers") should be("Matthew Powers")
    }

    it("will error out if a required argument is missing") {
      def fullName(firstName: String, lastName: String): String =
        firstName + " " + lastName
//      fullName("Matthew") // compile error
    }

  }

}
