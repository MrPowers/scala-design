name := "scala-design"

version := "0.0.1"

scalaVersion := "2.13.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"

fork in Test := true
javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled")
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
