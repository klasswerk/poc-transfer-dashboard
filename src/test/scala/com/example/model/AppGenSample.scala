package com.example.model

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import play.api.libs.json._

/**
  * Simple example to illustrate events
  */
object AppGenSample extends App {

  val fmt = ISODateTimeFormat.dateTime

  implicit def str2TimeStamp(dt: DateTime): TimeStamp = {
    TimeStamp(Time.dateTimeToString(dt))
  }

  implicit object AckFormat extends Format[AckState] {
    def reads(json: JsValue) =
      json match {
        case JsString("AckPending") => JsSuccess(AckPending)
        case JsString("AckReceived") => JsSuccess(AckReceived)
        case _ => JsError("cannot parse it")
      }

    def writes(ackState: AckState) =
    ackState match {
      case AckPending => JsString("AckPending")
      case AckReceived => JsString("AckReceived")
    }
  }

  implicit object SendOrReceiveFormat extends Format[SendOrReceive] {
    def reads(json: JsValue) =
      json match {
        case JsString("Send") => JsSuccess(Send)
        case JsString("Received") => JsSuccess(Receive)
        case _ => JsError("cannot parse it")
      }

    def writes(sOrR: SendOrReceive) =
      sOrR match {
        case Send => JsString("Send")
        case Receive => JsString("Received")
      }
  }

  implicit val eventIdFormat = Json.format[EventId]
  implicit val partnerFormat = Json.format[Partner]
  implicit val timeStampFormat = Json.format[TimeStamp]

  implicit val eventFormat = Json.format[Event]

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
      val json = Json.toJson(e)
      println("random event = [" + e + "]")
      println("JSON = [" + json + "]")
      strings = strings :+ json.toString
  }

  println("*******")
  println(Json.fromJson[Event](Json.parse(strings.head)))

  val start = TimeStamp("2017-05-25T00:00:00.000-07:00")
  val end = TimeStamp("2017-05-26T00:00:15.000-07:00")
  Generator.generateEvents(start, end)
}