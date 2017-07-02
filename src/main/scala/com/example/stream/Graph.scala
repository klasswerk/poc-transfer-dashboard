package com.example.stream

import akka.Done
import akka.actor.ActorSystem
import akka.kafka.ConsumerMessage.CommittableOffsetBatch
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Consume messages from kafka topic
  */
object Graph extends App {

  val conf = ConfigFactory.load

  implicit val system = ActorSystem("graphSystem", conf)
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  def terminateWhenDone(result: Future[Done]): Unit = {
    result.onComplete {
      case Failure(e) =>
        system.log.error(e, e.getMessage)
        system.terminate()
      case Success(_) => system.terminate()
    }
  }

  val topic = conf.getString("kafka_graph.topic")

  val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
    .withBootstrapServers(conf.getString("kafka_graph.bootstrap.servers"))
    .withGroupId(conf.getString("kafka_graph.group.id"))
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  def printMsg(data: String): Future[Done] = {
    println(s"Kafka Message: $data")
    Future.successful(Done)
  }

  val done =
    Consumer.committableSource(consumerSettings, Subscriptions.topics(topic))
      .mapAsync(1) { msg =>
        printMsg(msg.record.value).map(_ => msg.committableOffset)
      }
      .batch(max = 20, first => CommittableOffsetBatch.empty.updated(first)) { (batch, elem) =>
        batch.updated(elem)
      }
      .mapAsync(3)(_.commitScaladsl())
      .runWith(Sink.ignore)
  // #atLeastOnce

  terminateWhenDone(done)

}
