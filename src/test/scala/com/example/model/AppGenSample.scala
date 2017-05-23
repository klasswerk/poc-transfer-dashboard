package com.example.model

import play.api.libs.json._

/**
  * Simple example to illustrate events
  */
object AppGenSample extends App {

  implicit def str2TimeStamp(x: String): TimeStamp = {
    TimeStamp(x)
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
    Generator.getRandomEvent("100"),
    Generator.getRandomEvent("101"),
    Generator.getRandomEvent("102"),
    Generator.getRandomEvent("103"),
    Generator.getRandomEvent("104"),
    Generator.getRandomEvent("105")
  )

  events.foreach {
    e =>
      println("random event = [" + e + "]")
      println("JSON = [" + Json.toJson(e) + "]")
  }

  val json1 = """{"eventId":{"id":"6"},"partner":{"name":"YYZ"},"timestamp":{"isoTime":"105"},"sendReceive":"Received","size":167592,"ackState":"AckPending"}"""

  println(Json.fromJson[Event](Json.parse(json1)))
}
