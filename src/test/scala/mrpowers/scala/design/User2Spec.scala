package mrpowers.scala.design

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class User2Spec extends FunSpec {

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
