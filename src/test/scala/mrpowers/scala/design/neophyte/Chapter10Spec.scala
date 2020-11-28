package mrpowers.scala.design.neophyte

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class Chapter10Spec extends AnyFunSpec with Matchers {

  case class Email(
    subject: String,
    text: String,
    sender: String,
    recipient: String)

  type EmailFilter = Email => Boolean

  // we want to be able to block certain emails
  def newMailsForUser(mails: Seq[Email], f: EmailFilter) = mails.filter(f)

  // this is the clunky / verbose implementation
  val sentByOneOf: Set[String] => EmailFilter =
    senders => email => senders.contains(email.sender)

  val notSentByAnyOf: Set[String] => EmailFilter =
    senders => email => !senders.contains(email.sender)

  val minimumSize: Int => EmailFilter =
    n => email => email.text.length >= n

  val maximumSize: Int => EmailFilter =
    n => email => email.text.length <= n

  it("shows a basic use case of the filter") {
    val emailFilter: EmailFilter = notSentByAnyOf(Set("johndoe@example.com"))
    val mails = Email(
      subject = "It's me again, your stalker friend!",
      text = "Hello my friend! How are you?",
      sender = "johndoe@example.com", // this email is from a denylist email sender
      recipient = "me@example.com") :: Nil
    // the email is filtered cause it's sent by a denylist sender
    newMailsForUser(mails, emailFilter) should be(List[Email]())
  }

  // cleaner implementation
  type SizeChecker = Int => Boolean

  val sizeConstraint: SizeChecker => EmailFilter =
    f => email => f(email.text.length)

  val minimumSize2: Int => EmailFilter =
    n => sizeConstraint(_ >= n)

  val maximumSize2: Int => EmailFilter =
    n => sizeConstraint(_ <= n)

  // this is a super-generic function
  def complement[A](predicate: A => Boolean) =
    (a: A) => !predicate(a)

  val notSentByAnyOf2 = sentByOneOf andThen(complement(_))

  it("shows a supposedly better implementation of filter") {
    val emailFilter: EmailFilter = notSentByAnyOf2(Set("johndoe@example.com"))
    val mails = Email(
      subject = "It's me again, your stalker friend!",
      text = "Hello my friend! How are you?",
      sender = "johndoe@example.com", // this email is from a denylist email sender
      recipient = "me@example.com") :: Nil
    newMailsForUser(mails, emailFilter) should be(List[Email]())
  }

  // helper functions for composing predicates

  def any[A](predicates: (A => Boolean)*): A => Boolean =
    a => predicates.exists(pred => pred(a))

  def none[A](predicates: (A => Boolean)*) : A => Boolean =
    complement(any(predicates: _*))

//  def every[A](predicates: (A => Boolean)*): A => Boolean =
//    none(predicates.view.map(complement(_)): _*)

  // here's the user's configuration
//  val filter: EmailFilter = every(
//    notSentByAnyOf2(Set("johndoe@example.com")),
//    minimumSize(100),
//    maximumSize(10000)
//  )

  val addMissingSubject = (email: Email) =>
    if (email.subject.isEmpty) email.copy(subject = "No subject")
    else email

  val checkSpelling = (email: Email) =>
    email.copy(text = email.text.replaceAll("your", "you're"))

  val removeInappropriateLanguage = (email: Email) =>
    email.copy(text = email.text.replaceAll("dynamic typing", "**CENSORED**"))

  val addAdvertismentToFooter = (email: Email) =>
    email.copy(text = email.text + "\nThis mail sent via Super Awesome Free Mail")

  // create a pipeline of transformations
  val pipeline = Function.chain(Seq(
    addMissingSubject,
    checkSpelling,
    removeInappropriateLanguage,
    addAdvertismentToFooter))

}
