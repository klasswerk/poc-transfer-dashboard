package com.example.timeseries

import java.util.concurrent.TimeUnit

import com.paulgoldbaum.influxdbclient._
import org.scalatest.{FlatSpec, Matchers}

/**
  *
  */
class InfluxDBDriverSpec extends FlatSpec with Matchers {


  "The InfluxDBDriver" should "add all fields" in {

    val fields: Seq[(String, Any)] =
      List(
        ("testDouble", 1.0),
        ("testLong", 1L),
        ("testString", "1"),
        ("testBoolean", true)).toSeq

    val tspoint = ExplicitTimeSeriesPoint(
      "test_measurement",
      None,
      fields,
      None,
      TimeUnit.MILLISECONDS)

    val influxDBPoint = InfluxDBDriver.buildPoint(tspoint)

    influxDBPoint.fields should contain (DoubleField("testDouble", 1.0))
    influxDBPoint.fields should contain (LongField("testLong", 1L))
    influxDBPoint.fields should contain (StringField("testString", "1"))
    influxDBPoint.fields should contain (BooleanField("testBoolean", true))
  }

  "it" should "add all tags" in {

    val tags: Seq[(String, Any)] =
      List(
        ("tagKey1", "tagValue1"),
        ("tagKey2", "tagValue2")
      )

    val fields: Seq[(String, Any)] =
      List(
        ("testDouble", 1.0),
        ("testLong", 1L),
        ("testString", "1"),
        ("testBoolean", true)).toSeq

    val tspoint = ExplicitTimeSeriesPoint(
      "test_measurement",
      Some(tags),
      fields,
      None,
      TimeUnit.MILLISECONDS)

    val influxDBPoint = InfluxDBDriver.buildPoint(tspoint)

    influxDBPoint.tags should contain (Tag("tagKey1", "tagValue1"))
    influxDBPoint.tags should contain (Tag("tagKey2", "tagValue2"))
  }
}
