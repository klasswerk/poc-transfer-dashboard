package com.example.model

import java.util.concurrent.TimeUnit

import com.example.timeseries.{ExplicitTimeSeriesPoint, TimeSeriesPoint}

/**
  *
  */
object EventPoint {

  private def transferType(event: Event) =  {
    event match {
      case Event(_, _, _, Send, _, _, None, _) => "send"
      case Event(_, _, _, Receive, _, _, None, _) => "receive"
      case Event(_, _, _, Error, _, _, None, _) => "error"
      case Event(_, _, _, Send, _, _, Some(_), _) => "sendasync"
      case Event(_, _, _, Receive, _, _, Some(_), _) => "receiveasync"
      case Event(_, _, _, Error, _, _, Some(_), _) => "errorasync"
    }
  }


  def buildSizeTimeSeries(event: Event) : TimeSeriesPoint = {

    val tags: Seq[(String, Any)] =
      List(
        ("id", event.eventId.id),
        ("partner", event.partner.name),
        ("type", transferType(event)))

    val fields: Seq[(String, Any)] =
      List(
        ("filesize", event.size.toLong))

    ExplicitTimeSeriesPoint(
      "transferred_bytes",
      Some(tags),
      fields,
      // Some(System.currentTimeMillis()),
      None,
      TimeUnit.MILLISECONDS)
  }

  def buildSecondTimeSeries(event: Event) : TimeSeriesPoint = {

    val tags: Seq[(String, Any)] =
      List(
        ("id", event.eventId.id),
        ("partner", event.partner.name),
        ("type", transferType(event)))

    val fields: Seq[(String, Any)] =
      List(
        ("seconds", event.seconds.toLong))

    ExplicitTimeSeriesPoint(
      "transfer_seconds",
      Some(tags),
      fields,
      // Some(System.currentTimeMillis()),
      None,
      TimeUnit.MILLISECONDS)
  }

  def buildTimeSeriesPoints(event: Event): Seq[TimeSeriesPoint] = {
      List(buildSizeTimeSeries(event), buildSecondTimeSeries(event))
  }
}
