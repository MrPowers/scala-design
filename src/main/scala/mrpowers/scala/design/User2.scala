package mrpowers.scala.design

trait User2 {
  def name: String
  def score: Int
}
class FreeUser2(val name: String, val score: Int, val upgradeProbability: Double) extends User2

class PremiumUser2(val name: String, val score: Int) extends User2

object FreeUser2 {
  def unapply(user: FreeUser2): Option[(String, Int, Double)] =
    Some((user.name, user.score, user.upgradeProbability))
}

object PremiumUser2 {
  def unapply(user: PremiumUser2): Option[(String, Int)] = Some((user.name, user.score))
}
