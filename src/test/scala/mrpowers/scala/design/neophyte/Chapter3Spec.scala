package mrpowers.scala.design.neophyte

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class Chapter3Spec extends AnyFunSpec with Matchers {

  case class Player(name: String, score: Int)

  def message(player: Player) = player match {
    case Player(_, score) if score > 100000 => "Get a job"
    case Player(name, _) => "Hey " + name + ", nice to see you again!"
  }

  it("pattern matches expressions") {
    val p1 = Player("bob", 10)
    message(p1) should be("Hey bob, nice to see you again!")
    val p2 = Player("carolina", 500000)
    message(p2) should be("Get a job")
  }

  it("allows for Tuples to be destructured") {
    val (country, continent) = ("Albania", "Europe")
    country should be("Albania")
    continent should be("Europe")
  }

  it("pattern matches for comprehensions") {
    val gameResults = ("Daniel", 3500) :: ("Melissa", 13000) :: ("John", 7000) :: Nil

    val res1 = for {
      (name, score) <- gameResults
      if (score > 5000)
    } yield name

    res1 should be(List("Melissa", "John"))
  }

  it("returns sizes of all non-empty lists") {
    val lists = List(1, 2, 3) :: List.empty :: List(5, 3) :: Nil

    val res = for {
      list @ head :: _ <- lists
    } yield list.size

    res should be(List(3, 2))
  }

}
