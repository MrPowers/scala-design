package mrpowers.scala.design

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class UserSpec extends AnyFunSpec with Matchers {

  it("can extract from objects") {
    val user1 = new FreeUser("Daniel")
    FreeUser.unapply(user1) should be (Some("Daniel"))
  }

  it("uses extractors for pattern matching") {
    val user: User = new PremiumUser("Daniel")
    val res = user match {
      case FreeUser(name) => "Hello " + name
      case PremiumUser(name) => "Welcome back, dear " + name
    }
    res should be("Welcome back, dear Daniel")
  }

}
