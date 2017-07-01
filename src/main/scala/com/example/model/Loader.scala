package com.example.model

import cakesolutions.kafka.KafkaProducer.Conf
import cakesolutions.kafka.{KafkaProducer, KafkaProducerRecord}
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer

import scala.concurrent.Future

/**
  * Load Event into kafka topic
  */
object Loader {

  val conf = ConfigFactory.load
  val topic = conf.getString("kafka_loader.topic")

  val producer : KafkaProducer[String, String] = {
    val conf = ConfigFactory.load
    KafkaProducer(Conf(conf.getConfig("kafka_loader.kafka_producer"), new StringSerializer(), new StringSerializer()))
  }

  def loadEvent(event: Event) : Future[RecordMetadata] = {
    val record = KafkaProducerRecord(topic, event.partner.toString, JsonCodex.eventToJson(event))
    producer.send(record)
  }
}
