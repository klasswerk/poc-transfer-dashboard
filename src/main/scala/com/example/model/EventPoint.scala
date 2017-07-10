package com.example.model

import java.util.concurrent.TimeUnit

import com.example.timeseries.{ExplicitTimeSeriesPoint, TimeSeriesPoint}

/**
  * Object to create timeseries points from Events.
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


  /**
    * Create timeseries point for size from Events.
    */
  def buildSizeTimeSeries(event: Event) : TimeSeriesPoint = {

    val tags: Seq[(String, Any)] =
      List(
        // ("id", event.eventId.id),
        ("partner", event.partner.name),
        ("type", transferType(event)))

    val fields: Seq[(String, Any)] =
      List(
        ("filesize", event.size.toLong))

    ExplicitTimeSeriesPoint(
      "transferred_bytes",
      Some(tags),
      fields,
      Some(event.timestamp.dt.toDate.getTime),
      TimeUnit.MILLISECONDS)
  }
  /**
    * Create timeseries point for seconds from Events.
    */
  def buildSecondsTimeSeries(event: Event) : TimeSeriesPoint = {

    val tags: Seq[(String, Any)] =
      List(
        // ("id", event.eventId.id),
        ("partner", event.partner.name),
        ("type", transferType(event)))

    val fields: Seq[(String, Any)] =
      List(
        ("seconds", event.seconds.toLong))

    ExplicitTimeSeriesPoint(
      "transfer_seconds",
      Some(tags),
      fields,
      Some(event.timestamp.dt.toDate.getTime),
      TimeUnit.MILLISECONDS)
  }

  /**
    * Create all timeseries for bulk insert.
    */
  def buildTimeSeriesPoints(event: Event): Seq[TimeSeriesPoint] = {
      List(buildSizeTimeSeries(event), buildSecondsTimeSeries(event))
  }
}
