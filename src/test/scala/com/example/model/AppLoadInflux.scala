package com.example.model

import java.util.concurrent.TimeUnit

import com.example.timeseries.{ExplicitTimeSeriesPoint, InfluxDBDriver}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Simple example to illustrate events
  */
object AppLoadInflux extends App {

  val db = InfluxDBDriver("localhost", 8086, "my_database")

  val tspoint = ExplicitTimeSeriesPoint(
    "readings",
    Some(List(("sensor","3"))),
    List(("temp", 27.0)),
    Some(System.currentTimeMillis()),
    TimeUnit.MILLISECONDS)

  val bool_f = db.insertPoint(tspoint)

  Await.result(bool_f, 7.seconds)

  db.close
}