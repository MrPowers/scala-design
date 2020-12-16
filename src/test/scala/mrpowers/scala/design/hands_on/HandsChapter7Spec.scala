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

    it("deletes a file if it exists") {
      // doesn't error out when the file doesn't exist
      val file = os.pwd/"cats"/"cat1.txt"
      if (os.exists(file)) os.remove(file)

      os.makeDir(os.pwd/"cats")
      os.write(file, "prrrrr")
      if (os.exists(file)) os.remove(file)
//      println(os.list(os.pwd/"dogs"))
//      os.remove.all(os.pwd/"dogs")
    }

    it("deletes a folder recursively") {
      // make folder
      os.makeDir(os.pwd/"dogs")
      os.write(os.pwd/"dogs"/"dog1.txt", "ruff ruff")
      os.write(os.pwd/"dogs"/"dog2.txt", "bow wow")
      os.write(os.pwd/"dogs"/"dog3.txt", "rrrrr")

      // make subfolder
      os.makeDir(os.pwd/"dogs"/"frenchie")
      os.write(os.pwd/"dogs"/"frenchie"/"tio1.txt", "i am cute")

      os.remove.all(os.pwd/"dogs")
    }

    it("deletes all files with the jpg extension") {
      os.makeDir(os.pwd/"pics")
      os.write(os.pwd/"pics"/"family.jpg", "")
      os.write(os.pwd/"pics"/"cousins.txt", "")
      os.write(os.pwd/"pics"/"gf.jpg", "")
      os.write(os.pwd/"pics"/"friend.gif", "")

      os.list(os.pwd/"pics").filter(_.ext == "jpg").map(os.remove)
    }

    it("moves a lot of files") {
      //setup
//      os.makeDir(os.pwd/"public")
//      os.write(os.pwd/"public"/"family.jpg", "")
//      os.write(os.pwd/"public"/"cousins.txt", "")
//      os.write(os.pwd/"public"/"gf.jpg", "")
//      os.write(os.pwd/"public"/"friend.gif", "")
//      os.makeDir(os.pwd/"pub2")

      // move all the files from one folder to another
      os.list(os.pwd/"public").map(os.move.into(_, os.pwd/"pub2"))
    }

    it("makes it easy to filter a list") {
      // https://stackoverflow.com/questions/48162153/get-list-of-files-from-directory-in-scala
      os.makeDir(os.pwd/"local_data")
      os.write(os.pwd/"local_data"/"03_ssa_fruits.csv", "")
      os.write(os.pwd/"local_data"/"03_ssa_can.csv", "")
      os.write(os.pwd/"local_data"/"03_ssa_veg.csv", "")
      os.write(os.pwd/"local_data"/"sxattru.csv", "")
      os.write(os.pwd/"local_data"/"sxprod.csv", "")
      os.write(os.pwd/"local_data"/"sxstore.csv", "")

//      println(os.list(os.pwd/"local_data"/"03_ssa_fruits.csv").segments)

//        println(os.list(os.pwd/"local_data").last.segments.toList)

      println(os.list(os.pwd/"local_data").filter(_.segments.toList.last.startsWith("03_ssa")))
//      println(os.list(os.pwd/"local_data").filter((o: os.Path) => o.startsWith(os.Path("03_ssa"))))
    }

    it("appends to a file") {
      os.makeDir(os.pwd/"whatever")
      os.write(os.pwd/"whatever"/"file1.txt", "hi there")
      os.write.append(os.pwd/"whatever"/"file1.txt", "\nhear me moo")
    }

    it("recursively lists files") {
      os.makeDir(os.pwd/"countries")
      os.makeDir(os.pwd/"countries"/"colombia")
      os.write(os.pwd/"countries"/"colombia"/"medellin.txt", "q mas pues")
      os.write(os.pwd/"countries"/"colombia"/"a_something.foo", "soy un rolo")
      os.makeDir(os.pwd/"countries"/"brasil")
      os.write(os.pwd/"countries"/"brasil"/"a_whatever.foo", "carnaval")
      os.write(os.pwd/"countries"/"brasil"/"a_city.txt", "carnaval")

//      println(os.walk(os.pwd/"countries"))
      // recursively list all files
      println(os.walk(os.pwd/"countries").filter(os.isFile(_)))

      // recursively list all files that match a_*.foo
      println(os.walk(os.pwd/"countries").filter(_.segments.toList.last matches "a.*\\.foo"))
    }

  }

}
