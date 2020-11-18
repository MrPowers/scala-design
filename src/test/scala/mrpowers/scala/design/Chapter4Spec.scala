package mrpowers.scala.design

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class Chapter4Spec extends AnyFunSpec with Matchers {

  it("lowercases all the elements in a list") {
    val songTitles = List("The White Hare", "Childe the Hunter", "Take no Rogues")
    val res1 = songTitles.map(t => t.toLowerCase)
    res1 should be(List("the white hare", "childe the hunter", "take no rogues"))
    // use Scala's placeholder syntax to make the code shorter
    val res2 = songTitles.map(_.toLowerCase)
    res2 should be(List("the white hare", "childe the hunter", "take no rogues"))
  }

  val wordFrequencies = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) ::
    ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil

  it("demonstrates how filter and map are ugly when iterating over sequences") {
    // grab words that appear between 3 and 25 times
    val res  = wordFrequencies.filter(wf => wf._2 > 3 && wf._2 < 25).map(_._1)
    res should be(List("habitual", "homely", "society"))
  }

  it("shows how pattern matching anonymous functions are cleaner") {
    val res = wordFrequencies.filter { case (_, f) => f > 3 && f < 25 } map { case (w, _) => w }
    res should be(List("habitual", "homely", "society"))
  }

  it("shows how to do this with a single loop iteration") {
    // this is better because we don't unnecessarily do two loops
    val res = wordFrequencies.collect { case (word, freq) if freq > 3 && freq < 25 => word }
    res should be(List("habitual", "homely", "society"))
  }

}
