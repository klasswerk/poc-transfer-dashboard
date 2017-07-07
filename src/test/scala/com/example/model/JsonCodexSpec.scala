package com.example.model

import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

/**
  *
  */
class JsonCodexSpec extends FlatSpec with Matchers {

  val JSON =
    "{" +
    "\"eventId\":{\"id\":\"1\"}," +
    "\"partner\":{\"name\":\"A\"}," +
    "\"timestamp\":\"2017-01-01T13:00:00.000-08:00\"," +
    "\"action\":\"Send\"," +
    "\"size\":8192," +
    "\"seconds\":1" +
    "}"

  val event = Event(
    EventId("1"),
    Partner("A"),
    TimeStamp(new DateTime(2017, 1, 1, 13, 0, 0)),
    Send,
    8192,
    1,
    None,
    None)

  "The JsonCodex" should "convert event to JSON" in {

    JsonCodex.eventToJson(event) should equal (JSON)
  }

  "it" should "convert JSON to event" in {
    JsonCodex.jsonToEvent(JSON).get should equal (event)
  }

  "it" should "round-trip event" in {
    JsonCodex.jsonToEvent(JsonCodex.eventToJson(event)).get should equal (event)
  }

  "it" should "round-trip JSON" in {
    JsonCodex.eventToJson(JsonCodex.jsonToEvent(JSON).get) should equal (JSON)
  }
}
