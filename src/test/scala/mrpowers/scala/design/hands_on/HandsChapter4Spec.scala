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

  }

  describe("views") {
    it("shows the inefficient way of chaining array methods") {
      val arr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
      val res = arr.map(_ + 1).filter(_ % 2 == 0).slice(1, 3)
      res should equal(Array(4, 6))
    }

//    it("shows how to perform this operation efficiently with views") {
//      val arr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
//      val res = arr.view.map(_ + 1).filter(_ % 2 == 0).slice(1, 3).to(Array)
//      res should equal(Array(4, 6))
//    }
  }

}
