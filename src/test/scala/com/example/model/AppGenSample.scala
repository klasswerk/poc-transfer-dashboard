package com.example.model

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import play.api.libs.json._

import scala.util.{Failure, Success, Try}

/**
  * Simple example to illustrate events
  */
object AppGenSample extends App {

  val fmt = ISODateTimeFormat.dateTime

  implicit def dt2TimeStamp(dt: DateTime): TimeStamp = {
    TimeStamp(dt)
  }

  implicit object TimeStampFormat extends Format[TimeStamp] {
    def reads(json: JsValue) =
      json match {
        case JsString(str) =>
          Try(Time.stringToDateTime(str)) match {
            case Success(dt) => JsSuccess(TimeStamp(dt))
            case Failure(e) => JsError("parse error: " + e.getMessage)
          }
        case _ => JsError("cannot parse it")
      }

    def writes(timestamp: TimeStamp) =
      timestamp match {
        case TimeStamp(dt) => JsString(Time.dateTimeToString(dt))
      }
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

  implicit object ActionFormat extends Format[Action] {
    def reads(json: JsValue) =
      json match {
        case JsString("Send") => JsSuccess(Send)
        case JsString("Received") => JsSuccess(Receive)
        case JsString("Error") => JsSuccess(Error)
        case _ => JsError("cannot parse it")
      }

    def writes(action: Action) =
      action match {
        case Send => JsString("Send")
        case Receive => JsString("Received")
        case Error => JsString("Error")
      }
  }

  implicit val eventIdFormat = Json.format[EventId]
  implicit val partnerFormat = Json.format[Partner]

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

  val start = TimeStamp(Time.stringToDateTime("2017-05-25T00:00:00.000-07:00"))
  val end = TimeStamp(Time.stringToDateTime("2017-05-26T00:00:15.000-07:00"))
  Generator.generateEvents(start, end)(println _)
}