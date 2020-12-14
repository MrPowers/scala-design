package mrpowers.scala.design.hands_on

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class HandsChapter7Spec extends AnyFunSpec with Matchers {

  describe("paths") {
    it("return the present working directory") {
      println(os.pwd) // os.Path = /Users/powers/Documents/code/my_apps/scala-design
      println(os.pwd / os.up) // os.Path = /Users/powers/Documents/code/my_apps
      println(os.pwd / os.up / os.up) // os.Path = /Users/powers/Documents/code
    }

    it("returns the root path") {
      print(os.root)
    }

    it("prints the home path") {
      println(os.home) // os.Path = /Users/powers
    }

    it("creates a list of the path segments") {
      println(os.home.segments.toList) // List("Users", "powers")
    }

    it("lets you construct paths") {
      println(os.home / "GitHub" / "blog") // os.Path = /Users/powers/GitHub/blog
    }
  }

  describe("blog post") {
    it("copies a file into tmp with os-lib") {
      os.copy(
        os.pwd/"src"/"test"/"resources"/"people.csv",
        os.pwd/"tmp"/"people_copy.csv"
      )
    }

    it("creates a directory and file") {
      os.makeDir(os.pwd/"my_folder")
      os.write(os.pwd/"my_folder"/"story.txt", "once upon a time")
    }

    it("deletes a file") {
      os.remove(os.pwd/"my_folder"/"story.txt")
    }

    it("deletes a folder") {
      os.remove(os.pwd/"my_folder")
    }

    it("lists the contents of a folder") {
      os.makeDir(os.pwd/"dogs")
      os.write(os.pwd/"dogs"/"dog1.txt", "ruff ruff")
      os.write(os.pwd/"dogs"/"dog2.txt", "bow wow")
      os.write(os.pwd/"dogs"/"dog3.txt", "rrrrr")
      println(os.list(os.pwd/"dogs"))
      os.remove.all(os.pwd/"dogs")
    }
  }

}
