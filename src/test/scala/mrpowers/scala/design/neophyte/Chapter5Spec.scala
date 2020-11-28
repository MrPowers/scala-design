package mrpowers.scala.design.neophyte

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class Chapter5Spec extends AnyFunSpec with Matchers {

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

  it("shows how to raise a NullPointerException") {
    val something = null

    def getLength(x: String): Int = x.length

    intercept[java.lang.NullPointerException] {
      getLength(something)
    }
  }

  it("shows a terrible way to avoid a NullPointerException") {
    val something = null
    def crappyGetLength(x: String): Any =
      if (x != null) x.length else null
    assert(crappyGetLength(something) == null)
  }

  it("shows an undesirable way to deal with an optional value") {
    def betterGetLength(x: Option[String]): Option[Int] =
      if (x.isDefined) Some(x.get.length) else None
    assert(betterGetLength(None) == None)
    assert(betterGetLength(Some("puppy")) == Some(5))
  }

  it("shows a good way to deal with an optional value") {
    def goodGetLength(x: Option[String]): Option[Int] =
      x.map(_.length)
    assert(goodGetLength(None) == None)
    assert(goodGetLength(Some("puppy")) == Some(5))
  }

  it("shows ugly nested option possibilities") {
    case class Dog(
      id: Int,
      firstName: String,
      nickName: Option[String])

    val dog1 = Dog(1, "Spot", Some("foofie"))
    val dog2 = Dog(2, "Luna", None)

    val dogs = Map(
      1 -> dog1,
      2 -> dog2)

    def findById(dogs: Map[Int, Dog], id: Int): Option[Dog] =
      dogs.get(id)

    val d1 = findById(dogs, 1)
    d1.map(_.nickName) should be(Some(Some("foofie")))
//    println(d1)
//    println(dog1.nickName)
  }

  it("shows how to avoid ugly nested optional values") {
    case class Dog(
                    id: Int,
                    firstName: String,
                    nickName: Option[String])

    val dog1 = Dog(1, "Spot", Some("foofie"))
    val dog2 = Dog(2, "Luna", None)

    val dogs = Map(
      1 -> dog1,
      2 -> dog2)

    def findById(dogs: Map[Int, Dog], id: Int): Option[Dog] =
      dogs.get(id)

    val d1 = findById(dogs, 1)
    d1.flatMap(_.nickName) should be(Some("foofie"))
  }

  it("shows how to filter optional values when the predicate is true") {
    case class Cat(name: String, age: Int)
    val darla = Some(Cat("darla", 12))
    darla.filter(_.age > 10) should be(darla)
  }

  it("shows how to filter optional values when the predicate is false") {
    case class Cat(name: String, age: Int)
    val chunkers = Some(Cat("chunkers", 4))
    chunkers.filter(_.age > 10) should be(None)
  }

  it("shows how to filter over None values") {
    case class Cat(name: String, age: Int)
    val nala: Option[Cat] = None
    nala.filter(_.age > 10) should be(None)
  }

}
