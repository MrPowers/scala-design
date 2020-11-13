package mrpowers.scala.design

import org.scalatest.{FunSpec, Matchers}

class Chapter5Spec extends FunSpec with Matchers {

  it("shows how to create some basic optional values") {
    val greeting1: Option[String] = Some("Hello world")
    greeting1 should be(Some("Hello world"))
    val greeting2: Option[String] = None
    greeting2 should be(None)
  }

  case class User(
    id: Int,
    firstName: String,
    lastName: String,
    age: Int,
    gender: Option[String])

  val user1 = User(1, "John", "Doe", 32, Some("male"))
  val user2 = User(2, "Johanna", "Smith", 30, None)

  object UserRepository {
    private val users = Map(
      1 -> user1,
      2 -> user2)

    def findById(id: Int): Option[User] = users.get(id)

    def findAll = users.values
  }

  it("fetches a user with get") {
    val fetchedUser = UserRepository.findById(1).get
    fetchedUser should be(user1)
  }

  it("fetches an optional value with a default option") {
    user2.gender.getOrElse("not specified") should be("not specified")
  }

  it("pattern matches on an optional value") {
    val res = user2.gender match {
      case Some(gender) => "Gender: " + gender
      case None => "Gender: not specified"
    }
    res should be("Gender: not specified")
  }

  it("gets the age of a user with map") {
    val u1 = UserRepository.findById(1)
    u1.map(_.age) should be(Some(32))

    val u42 = UserRepository.findById(42)
    u42.map(_.age) should be(None)
  }

  it("shows how ugly fetching nested option values can be") {
    val u1 = UserRepository.findById(1)
    u1.map(_.gender) should be(Some(Some("male")))
  }

  it("shows how flatMap avoids this nesting optional values craziness") {
    val u1 = UserRepository.findById(1)
    u1.flatMap(_.gender) should be(Some("male"))

    val u42 = UserRepository.findById(42)
    u42.flatMap(_.gender) should be(None)
  }

  // context on how lists work because we're thinking of Optional values conceptually as a list that contains 0 or 1 value
  it("uppercases all the values in a nested list") {
    val names = List(List("John", "Johanna", "Daniel"), List(), List("Doe", "Westheide"))
    names.flatMap(_.map(_.toUpperCase)) should equal(List("JOHN", "JOHANNA", "DANIEL", "DOE", "WESTHEIDE"))
  }

  it("maps a list of optional strings") {
    val names = List(Some("Johanna"), None, Some("Daniel"))
    names.map(_.map(_.toUpperCase)) should equal(List(Some("JOHANNA"), None, Some("DANIEL")))
    names.flatMap(xs => xs.map(_.toUpperCase)) should equal(List("JOHANNA", "DANIEL"))
  }

  it("shows how to filter an option") {
    val u1 = UserRepository.findById(1)
    u1.filter(_.age > 30) should be(u1) // Some(user), because age is > 30

    val u2 = UserRepository.findById(2)
    u2.filter(_.age > 30) should be(None) // None, because age is <= 30

    val u3 = UserRepository.findById(3)
    u3.filter(_.age > 30) should be(None) // None, because user is already None
  }

  it("gets the gender for a single user") {
    val res = for {
      user <- UserRepository.findById(1)
      gender <- user.gender
    } yield gender
    res should be(Some("male"))
  }

}
