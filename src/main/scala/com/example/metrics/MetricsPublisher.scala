package com.example.metrics

import com.example.timeseries.InfluxDBDriver

import scala.concurrent.ExecutionContext.Implicits.global

/**
  *
  */
object MetricsPublisher {

  val db = InfluxDBDriver("localhost", 8086, "jvm")

  def publishMetrics() = {

    val points =
      Seq(
        JvmHeapPoint.getJvmHeapPoint,
        PendingFinalizationCountPoint.getPendingFinalizationCountPoint,
        ThreadCountPoint.getThreadCountPoint
      )

    db.insertPoints(points).onFailure {
      case e => println("Failed: " + e)
    }
  }
}
