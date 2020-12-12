package mrpowers.scala.design.hands_on

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class HandsChapter4Spec extends AnyFunSpec with Matchers {

  describe("builders") {
    it("lets you elegantly build arrays") {
      val b = Array.newBuilder[Int]
      b += 10
      b += 20
      b.result() should equal(Array(10, 20))
    }
  }

  describe("factory methods") {
    it("lets you easily create an array") {
      Array.fill(3)("hello") should equal(Array("hello", "hello", "hello"))
    }

    it("lets you combine arrays") {
      Array(1, 2) ++ Array(3, 4) should equal(Array(1, 2, 3, 4))
    }
  }

  describe("transforms") {
    it("multiplies every element in an array by two") {
      Array(1, 2, 3).map(i => i * 2) should equal(Array(2, 4, 6))
    }

    it("filters out the even elements") {
      Array(1, 2, 3, 4, 5).filter(i => i % 2 == 1) should equal(Array(1, 3, 5))
    }

    it("takes the first two elements") {
      Array(1, 2, 3, 4, 5).take(2) should equal(Array(1, 2))
    }

    it("drops the first two elements") {
      Array(1, 2, 3, 4, 5).drop(2) should equal(Array(3, 4, 5))
    }

    it("keeps elements from index 1-4") {
      Array(1, 2, 3, 4, 5).slice(1, 4) should equal(Array(2, 3, 4))
    }

    it("removes duplicates from an array") {
      Array(1, 1, 1, 2, 3, 2).distinct should equal(Array(1, 2, 3))
    }
  }

  describe("queries") {
    it("finds the first even values greater than 4") {
      val arr = Array(1, 2, 3, 4, 5, 6, 7, 8)
      arr.find(i => i % 2 == 0 && i > 4) should be(Some(6))
    }

    it("finds even values greater than 10") {
      val arr = Array(1, 2, 3)
      arr.find(i => i % 2 == 0 && i > 10) should be(None)
    }

    it("returns true if the Array contains a value greater than one") {
      val arr = Array(1, 2, 3)
      arr.exists(i => i > 1) should be(true)
    }

    it("returns false if the Array does not contains a value less than zero") {
      val arr = Array(1, 2, 3)
      arr.exists(i => i < 0) should be(false)
    }
  }

  describe("aggregations") {
    it("converts array elements into a comma delimited string") {
      val arr = Array("a", "b", "c")
      arr.mkString(",") should be("a,b,c")
    }

    it("does a fancy Array to string conversion") {
      val arr = Array("a", "b", "c")
      arr.mkString("{", ",", "}") should be("{a,b,c}")
    }
  }

  describe("foldLeft") {
    it("sums all the elements in an array") {
      val arr = Array(1, 2, 3, 4)
      arr.foldLeft(0)((memo, y) => memo + y) should be(10)
    }

    it("multiples all the elements in an array") {
      val arr = Array(1, 2, 3, 4)
      arr.foldLeft(1)((memo, y) => memo * y) should be(24)
    }

    it("multiples all the elements in an array with placeholder syntax") {
      val arr = Array(1, 2, 3, 4)
      arr.foldLeft(1)(_ * _) should be(24)
    }

    it("shows how for loops can also add all the elements in an array") {
      var total = 0
      for (i <- Array(1, 2, 3, 4)) total += i
      total should be(10)
    }
  }

  describe("views") {
    it("shows the inefficient way of chaining array methods") {
      val arr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
      val res = arr.map(_ + 1).filter(_ % 2 == 0).slice(1, 3)
      res should equal(Array(4, 6))
    }

    it("shows how to perform this operation efficiently with views") {
      val arr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
      val res = arr.view.map(_ + 1).filter(_ % 2 == 0).slice(1, 3).to(Array)
      res should equal(Array(4, 6))
    }
  }

  describe("converters") {
    it("converts an array to a vector") {
      val res = Array(1, 2, 3).to(Vector)
      res should equal(Vector(1, 2, 3))
    }

    it("converts a vector an an array") {
      val res = Vector(1, 2, 3).to(Array)
      res should equal(Array(1, 2, 3))
    }

    it("converts an Array to a Set") {
      val res = Array(1, 1, 2, 2, 3, 3).to(Set)
      res should equal(Set(1, 2, 3))
    }
  }

}
