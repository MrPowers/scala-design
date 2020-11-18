package mrpowers.scala.design

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class User2Spec extends AnyFunSpec with Matchers {

  it("uses an extractor for pattern matching") {
    val user: User2 = new FreeUser2("Daniel", 3000, 0.7d)
    val res = user match {
      case FreeUser2(name, _, p) =>
        if (p > 0.75) name + ", what can we do for you today?" else "Hello " + name
      case PremiumUser2(name, _) => "Welcome back, dear " + name
    }
    res should be("Hello Daniel")
  }

}
