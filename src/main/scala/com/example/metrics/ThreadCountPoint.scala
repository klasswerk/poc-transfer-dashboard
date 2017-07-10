package com.example.metrics

import java.util.concurrent.TimeUnit

import com.example.timeseries.{ExplicitTimeSeriesPoint, TimeSeriesPoint}

/**
  *
  */
object ThreadCountPoint {

  /**
    * Function to create a timeseries point for the number of threads
    * in the JVM.
    */
  def getThreadCountPoint: TimeSeriesPoint = {

    val tags: Seq[(String, Any)] =
      List(
        ("jvm", "Graph"))

    val fields: Seq[(String, Any)] =
      List(
        ("count", Platform.getThreadCount.get.toLong))

    ExplicitTimeSeriesPoint(
      "threads",
      Some(tags),
      fields,
      // Some(System.currentTimeMillis()),
      None,
      TimeUnit.MILLISECONDS)
  }
}
