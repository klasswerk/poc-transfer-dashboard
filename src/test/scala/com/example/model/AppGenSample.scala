package com.example.model

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Simple example to illustrate events
  */
object AppGenSample extends App {

  val fmt = ISODateTimeFormat.dateTime

  implicit def dt2TimeStamp(dt: DateTime): TimeStamp = {
    TimeStamp(dt)
  }


  println("random partner info = [" + Generator.getRandomPartnerMetaInfo + "]")

  val events = List(
    Generator.getRandomEvent(DateTime.now),
    Generator.getRandomEvent(DateTime.now),
    Generator.getRandomEvent(DateTime.now),
    Generator.getRandomEvent(DateTime.now),
    Generator.getRandomEvent(DateTime.now),
    Generator.getRandomEvent(DateTime.now)
  )

  var strings: List[String] = Nil
  events.foreach {
    e =>
      val json = JsonCodex.eventToJson(e)
      println("random event = [" + e + "]")
      println("JSON = [" + json + "]")
      strings = strings :+ json.toString
  }


  println("*******")
  println(JsonCodex.jsonToEvent(strings.head).getOrElse(Nil))

  val now = DateTime.now()
  val start = TimeStamp(now.minusHours(1))
  val end = TimeStamp(now)

  def loadSync(event: Event) : Unit = {

    val insert = Loader.loadEvent(event)

    Await.result (insert, 10 second)
    insert.value match {
      case Some(Success(_)) => // No need to print on Success
      case Some(Failure(e)) => println ("Insert into kafka failed: " + e)
      case None             => println ("Future not done")
    }
  }

  Generator.generateEvents(start, end)(loadSync)
}