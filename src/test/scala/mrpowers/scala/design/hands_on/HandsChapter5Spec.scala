package mrpowers.scala.design.hands_on

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class HandsChapter5Spec extends AnyFunSpec with Matchers {

  describe("case classes") {
    case class Point(x: Int, y: Int)
    val p1 = Point(1, 2)
    val p2 = Point(1, 2)

    it("shows how to create a case class and access certain fields") {
      p1.x should be(1)
      p1.y should be(2)
    }

    it("shows how to convert a case class to a string") {
      p1.toString should be("Point(1, 2)")
    }

    it("shows how to compare two points") {
      p1 == p2 should be(true)
    }

    it("shows how to create a modified object with the copy method") {
      p1.copy(x = 44) should be(Point(44, 2))
    }

    it("shows how to define methods in the body of the case class") {
      case class SomePerson(firstName: String, lastName: String) {
        def fullName = s"$firstName $lastName"
      }

      val dry = SomePerson("dry", "bones")
      dry.fullName should be("dry bones")
    }
  }

  describe("sealed trait") {
    sealed trait Point
    case class Point2D(x: Double, y: Double) extends Point
    case class Point3D(x: Double, y: Double, z: Double) extends Point
    def hypotenuse(p: Point) = p match {
      case Point2D(x, y) => math.sqrt(x * x + y * y)
      case Point3D(x, y, z) => math.sqrt(x * x + y * y + z * z)
    }
    it("demonstrates how to calculate the hypotenuse of a 2D point") {
      val res = hypotenuse(Point2D(1, 2))
      val delta = math.abs(res - 2.23)
      assert(delta < 0.01)
    }
    it("demonstrates how to calculate the hypotenuse of a 3D point") {
      val res = hypotenuse(Point3D(4, 5, 6))
      val delta = math.abs(res - 8.77)
      assert(delta < 0.01)
    }
  }

  describe("pattern matching") {
    it("matches on Ints") {
      def dayOfWeek(x: Int) = x match {
        case 1 => "Mon"
        case 2 => "Tue"
        case 3 => "Wed"
        case _ => "Unknown"
      }
      dayOfWeek(2) should be("Tue")
      dayOfWeek(-1) should be("Unknown")
    }

    it("matches on Strings") {
      def indexOfDay(d: String) = d match {
        case "Mon" => 1
        case "Tue" => 2
        case "Wed" => 3
        case _ => -1
      }
      indexOfDay("Wed") should be(3)
      indexOfDay("Whatever") should be(-1)
    }

    it("matches on Tuple(Int, Int)") {
      def fizzBuzz(num: Int) = (num % 3, num % 5) match {
        case (0, 0) => "FizzBuzz"
        case (0, _) => "Fizz"
        case (_, 0) => "Buzz"
        case _ => ""
      }
      fizzBuzz(3) should be("Fizz")
      fizzBuzz(15) should be("FizzBuzz")
    }

    it("matches on case classes") {
      case class Point(x: Int, y: Int)
      def direction(p: Point) = p match {
        case Point(0, 0) => "origin"
        case Point(_, 0) => "horizontal"
        case Point(0, _) => "vertical"
        case _ => "diagonal"
      }
      direction(Point(10, 0)) should be("horizontal")
      direction(Point(0, 5)) should be("vertical")
      direction(Point(2, 2)) should be("diagonal")
    }

    it("matches on string patterns") {
      def americanDate(s: String) = s match {
        case s"$day-$month-$year" => s"$month/$day/$year"
        case _ => "not a date"
      }
      americanDate("22-1-1965") should be("1/22/1965")
      americanDate("10-1988") should be("not a date")
    }

    it("allows for nested matches") {
      case class Person(name: String, title: String)
      def greet(p: Person) = p match {
        case Person(s"$firstName $lastName", title) => s"Hello $title $lastName"
        case Person(name, title) => s"Hello $title $name"
      }
      greet(Person("ron", "mac")) should be("ron mac")
      greet(Person("Matthew Powers", "Mr")) should be("Mr Powers")
    }

    it("shows how to pattern match in a for loop") {
      val a = Array[(Int, String)]((1, "one"), (2, "two"), (3, "three"))
      val res = for ((i, s) <- a) yield(s + i)
      res should equal(Array("one1", "two2", "three3"))
    }

    it("shows how to pattern match in val statements") {
      case class Coffee(origin: String, roast: String)
      val lomaverde = Coffee("Colombia", "medium")
      val Coffee(a, b) = lomaverde
      a should be("Colombia")
      b should be("medium")
    }
  }

  describe("by name parameters") {
    it("avoid evaluation where possible for performance sensitive applications") {
      var logLevel = 1
      def log(level: Int, msg: => String) = {
        if (level > logLevel) msg
      }
      // the second argument is evaluated
      log(2, "Hello " + 123 + " World") should be("Hello 123 World")
      logLevel = 5
      // the second argument IS NOT evaluated, important for performance sensitive applications
      log(2, "Hello " + 123 + " World") should be(())
    }

    it("defers evaluation to allow for functions that have setup / teardown code") {
      def measureTime(f: => Unit) = {
        val start = System.currentTimeMillis()
        f
        val end = System.currentTimeMillis()
        s"Evaluation took ${end - start} milliseconds"
      }
      val res = measureTime(new Array[String](10 * 1000 * 1000).hashCode())
      res should fullyMatch regex """Evaluation took \d\d milliseconds"""
    }

    it("can repeat evaluation") {

      def retry[T](max: Int)(f: => T): T = {
        var tries = 0
        var result: Option[T] = None
        while (result == None) {
          try { result = Some(f) }
          catch { case e: Throwable =>
            tries += 1
            if (tries > max) throw e
            else println(s"failed, retry #$tries")
          }
        }
        result.get
      }

      val httpbin = "https://httpbin.org"
      retry(max = 5) {
        // only succeeds with 200 responses  1/3 of the time
        requests.get(s"$httpbin/status/200,400,500")
      }

    }
  }

  describe("implicit parameters") {
    it("shows a simple implicit parameter example") {
      class Foo(val value: Int)
      def bar(implicit foo: Foo) = foo.value + 10
      implicit val foo: Foo = new Foo(1)
      bar should be(11)
      // can also pass in foo explicitly
      bar(foo) should be(11)
    }
  }

}
