package com.example.model

import java.util.concurrent.TimeUnit

import com.example.timeseries.{ExplicitTimeSeriesPoint, InfluxDBDriver, TimeSeriesPoint}

/**
  *
  */
object Converter {

  def buildTimeSeries(event: Event) : TimeSeriesPoint = {

    val tranferType = event match {
      case Event(_, _, _, Send, _, None, _) => "send"
      case Event(_, _, _, Receive, _, None, _) => "receive"
      case Event(_, _, _, Error, _, None, _) => "error"
      case Event(_, _, _, Send, _, Some(_), _) => "sendasync"
      case Event(_, _, _, Receive, _, Some(_), _) => "receiveasync"
      case Event(_, _, _, Error, _, Some(_), _) => "errorasync"
    }

    val tags: Seq[(String, Any)] =
      List(
        ("partner", event.partner.name),
        ("type", tranferType))

    val fields: Seq[(String, Any)] =
      List(
        ("filesize", event.size.toLong))

    ExplicitTimeSeriesPoint(
      "transfer",
      Some(tags),
      fields,
      // Some(System.currentTimeMillis()),
      None,
      TimeUnit.MILLISECONDS)
  }
}
