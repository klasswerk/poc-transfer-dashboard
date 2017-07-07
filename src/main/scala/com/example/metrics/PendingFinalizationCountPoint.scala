package com.example.metrics

import java.util.concurrent.TimeUnit

import com.example.timeseries.{ExplicitTimeSeriesPoint, TimeSeriesPoint}

/**
  *
  */
object PendingFinalizationCountPoint {

  def getPendingFinalizationCountPoint() : TimeSeriesPoint = {

    val tags: Seq[(String, Any)] =
      List(
        ("jvm", "Graph"))

    val fields: Seq[(String, Any)] =
      List(
        ("count", Platform.getThreadCount.get.toLong))

    ExplicitTimeSeriesPoint(
      "pendingFinalization",
      Some(tags),
      fields,
      // Some(System.currentTimeMillis()),
      None,
      TimeUnit.MILLISECONDS)
  }
}
