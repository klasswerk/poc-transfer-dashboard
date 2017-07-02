import Dependencies._

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += Resolver.bintrayRepo("cakesolutions", "maven")

lazy val commonSettings = Seq(
  organization := "com.example",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.11"
)

lazy val root = (project in file(".")).
  settings(
   commonSettings,

    name := "poc-transfer-dashboard",
    libraryDependencies ++= Seq(
      akkaActor,
      akkaStream,
      playJson,
      jodaTime,
      cakeKafkaClient,
      reactiveKafka,
      akkaTestkit % Test,
      scalaTest % Test)
  )

val startZookeeper = taskKey[String]("Start Zookeeper")
val stopZookeeper = taskKey[String]("Stop Zookeeper")
val startKafka = taskKey[String]("Start Kafka")
val stopKafka = taskKey[String]("Stop Kafka")

val kafkaBinDir = "/main/kafka_2.11-0.10.2.1/bin/"

val zkCfgFile = "config/zookeeper.properties"
val kafkaCfgFile = "config/server.properties"

val zkStart = kafkaBinDir + "zookeeper-server-start.sh -daemon " + zkCfgFile
val zkStop = kafkaBinDir + "zookeeper-server-stop.sh " + zkCfgFile

val kafkaStart = kafkaBinDir + "kafka-server-start.sh -daemon " + kafkaCfgFile
val kafkaStop = kafkaBinDir + "kafka-server-stop.sh " + kafkaCfgFile

lazy val kafka = (project in file("kafka")).
  settings(
    commonSettings,

    name := "poc-kafka",
    startZookeeper := Process(sourceDirectory.value + zkStart).!.toString,
    stopZookeeper := Process(sourceDirectory.value + zkStop).!.toString,
    startKafka := Process(sourceDirectory.value + kafkaStart).!.toString,
    stopKafka := Process(sourceDirectory.value + kafkaStop).!.toString
  )

val startCassandra = taskKey[String]("Start Cassandra")
val stopCassandra = taskKey[String]("Stop Cassandra")

val cassandraBinDir = "/main/apache-cassandra-3.10/bin/"

val cassandraStart = cassandraBinDir + "cassandra"
val cassandraStop = cassandraBinDir + "stop-server"

lazy val cassandra = (project in file("cassandra")).
  settings(
    commonSettings,

    name := "poc-cassandra",
    startCassandra := Process(sourceDirectory.value + cassandraStart).!.toString,
    stopCassandra := Process(sourceDirectory.value + cassandraStop).!.toString
  )
