package com.example.model

import play.api.libs.json._

import scala.util.{Failure, Success, Try}

object JsonCodex {

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

  def eventToJson(event: Event) : String = Json.toJson(event).toString()

  def jsonToEvent(json: String) : Option[Event] = {
    Json.fromJson[Event](Json.parse(json)).asOpt
  }
}
