package mrpowers.scala.design.hands_on

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class HandsChapter8Spec extends AnyFunSpec with Matchers {

  // you need to know how to serialize your Scala data structures to data formats
  // JSON and binary MessagePack are common serialization data formats
  // uPickle makes serialization / deserialization easy

  describe("read JSON file") {
    it("reads a JSON file") {
      val jsonString = os.read(os.pwd/"src"/"test"/"resources"/"phil.json")
      val data = ujson.read(jsonString)
      println(data)
      println("***")
      println(data("last_name"))
      println("***")
    }

    it("serializes an object into a JSON string in memory") {
      val small = ujson.Arr(
        ujson.Obj("hello" -> "world", "answer" -> 42),
        true
      )
      println(ujson.write(small))
    }

    it("writes out a JSON object to disk") {
      val small = ujson.Arr(
        ujson.Obj("hello" -> "world", "answer" -> 42),
        true
      )
      os.write(os.pwd/"tmp"/"small.json", small)
    }

    it("mutates a JSON object and writes it out") {
      val jsonString = os.read(os.pwd/"src"/"test"/"resources"/"phil.json")
      val data = ujson.read(jsonString)
      data("last_name") = "Poker Brat"
      os.write(os.pwd/"tmp"/"poker_brat.json", data)
    }

    it("reads a JSON file with an array column") {
      val jsonString = os.read(os.pwd/"src"/"test"/"resources"/"colombia.json")
      val data = ujson.read(jsonString)
      println(data("cities"))
    }

    it("writes JSON file with an array column") {
      val jsonString = os.read(os.pwd/"src"/"test"/"resources"/"colombia.json")
      val data = ujson.read(jsonString)
      data("cities").arr.append("Cartagena")
      os.write(os.pwd/"tmp"/"more_colombia.json", data)
    }

    it("builds up a JSON object in memory") {
      val brasil = ujson.Obj("population" -> "210 million")
      brasil("cities") = ujson.Arr("recife", "sao paolo")
      os.write(os.pwd/"tmp"/"brasil.json", brasil)
    }
  }

}
