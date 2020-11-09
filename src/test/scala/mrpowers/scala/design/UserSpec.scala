package mrpowers.scala.design

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class UserSpec extends FunSpec {

  it("can extract from objects") {
    val user1 = new FreeUser("Daniel")
    FreeUser.unapply(user1) should be (Some("Daniel"))
  }

}
