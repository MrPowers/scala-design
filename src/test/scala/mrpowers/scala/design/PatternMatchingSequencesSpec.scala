package mrpowers.scala.design

import org.scalatest.{FunSpec, Matchers}

class PatternMatchingSequencesSpec extends FunSpec with Matchers {

  it("pattern matches 2 or 3 elements") {
    val xs = 3 :: 6 :: 12 :: Nil
    val res = xs match {
      case List(a, b) => a * b
      case List(a, b, c) => a + b + c
      case _ => 0
    }
    res should be(21)
  }

  it("matches a list of arbitrary length with a wildcard operator") {
    val xs = 3 :: 6 :: 12 :: 24 :: Nil
    val res = xs match {
      case List(a, b, _*) => a * b
      case _ => 0
    }
    res should be(18)
  }

}
