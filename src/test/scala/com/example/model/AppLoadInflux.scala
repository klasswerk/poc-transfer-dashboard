package com.example.model

import java.util.concurrent.TimeUnit

import com.example.timeseries.{ExplicitTimeSeriesPoint, InfluxDBDriver}
import com.paulgoldbaum.influxdbclient.Parameter.Precision
import com.paulgoldbaum.influxdbclient.{InfluxDB, Point}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * Simple example to illustrate events
  */
object AppLoadInflux extends App {

  val db = InfluxDBDriver("localhost", 8086, "my_database")

  val tspoint = ExplicitTimeSeriesPoint(
    "readings",
    Some(List(("sensor","2"))),
    List(("temp", 33.0)),
    Some(System.currentTimeMillis()),
    TimeUnit.MILLISECONDS)

  val bool_f = db.insertPoint(tspoint)

  Await.result(bool_f, 7.seconds)

  db.close
}