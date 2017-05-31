import sbt._

object Dependencies {

  lazy val akkaVersion = "2.4.17"

  lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  lazy val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.0"

  lazy val jodaTime = "joda-time" % "joda-time" % "2.9.9"

  lazy val playJson = "com.typesafe.play" %% "play-json" % "2.5.15"

  lazy val cakeKafkaClient = "net.cakesolutions" %% "scala-kafka-client" % "0.10.2.2"
}
