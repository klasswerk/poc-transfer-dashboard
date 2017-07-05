import sbt._

object Dependencies {

  lazy val akkaVersion = "2.4.18"

  lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion

  lazy val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion

  lazy val playJson = "com.typesafe.play" %% "play-json" % "2.5.15"

  lazy val jodaTime = "joda-time" % "joda-time" % "2.9.9"

  lazy val influxClient = "com.paulgoldbaum" %% "scala-influxdb-client" % "0.5.2"

  lazy val cakeKafkaClient = "net.cakesolutions" %% "scala-kafka-client" % "0.10.2.2"
  lazy val reactiveKafka = "com.typesafe.akka" %% "akka-stream-kafka" % "0.16"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.0"
}
