package com.example.metrics

import com.example.timeseries.InfluxDBDriver

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  *
  */
object MetricsPublisher {

  val db = InfluxDBDriver("localhost", 8086, "jvm")

  def pubishMetrics() = {

    val points =
      Seq(
        JvmHeapPoint.getJvmHeapPoint(),
        PendingFinalizationCountPoint.getPendingFinalizationCountPoint(),
        ThreadCountPoint.getThreadCountPoint()
      )

    db.insertPoints(points).onComplete {
      case Success(result) =>
      case Failure(e) => println("Failed: " + e)
    }
  }
}
