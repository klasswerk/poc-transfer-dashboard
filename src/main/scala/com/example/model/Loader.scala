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

  val TOPIC = "loader.test"

  val producer : KafkaProducer[String, String] = {
    val conf = ConfigFactory.load
    KafkaProducer(Conf(conf, new StringSerializer(), new StringSerializer()))
  }

  def loadEvent(event: Event) : Future[RecordMetadata] = {
    val record = KafkaProducerRecord(TOPIC, event.partner.toString, JsonCodex.eventToJson(event))
    producer.send(record)
  }
}
