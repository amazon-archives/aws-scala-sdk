scalaVersion in ThisBuild := "2.11.6"
crossScalaVersions in ThisBuild := Seq("2.10.5", "2.11.6")

lazy val root = project
  .in(file("."))
  .settings(
    organization := "com.amazonaws",
    name := "aws-scala-sdk-kinesis",
    version := "0.0.1",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk-kinesis" % "1.9.27"
  )
