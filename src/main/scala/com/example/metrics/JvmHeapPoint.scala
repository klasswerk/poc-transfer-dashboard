package com.example.metrics

import java.util.concurrent.TimeUnit

import com.example.timeseries.{ExplicitTimeSeriesPoint, TimeSeriesPoint}

object JvmHeapPoint {

  /**
    * Function to create a timeseries point for the JVM heap usage.
    */
  def getJvmHeapPoint: TimeSeriesPoint = {

    val tags: Seq[(String, Any)] =
      List(
        ("jvm", "Graph"))

    val fields: Seq[(String, Any)] =
      List(
        ("bytes", Platform.getHeapMemory.get))

    ExplicitTimeSeriesPoint(
      "heap",
      Some(tags),
      fields,
      // Some(System.currentTimeMillis()),
      None,
      TimeUnit.MILLISECONDS)
  }
}
