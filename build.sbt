import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.2"
    )),
    name := "poc-transfer-dashboard",
    libraryDependencies ++= Seq(akkaActor, akkaTestkit % Test, scalaTest % Test)
  )
