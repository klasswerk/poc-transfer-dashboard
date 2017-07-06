package com.example.timeseries

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Generic TimeSeries Database driver
  */
trait DBDriver {

  def insertPoint(timeSeriesPoint: TimeSeriesPoint) : Future[Boolean]

  def insertPoints(timeSeriesPoints: Seq[TimeSeriesPoint]) : Future[Boolean] = {

    val future_seq = timeSeriesPoints.map(insertPoint)
    Future.sequence(future_seq).map(_.foldLeft(true) { (e,acc) => e && acc} )
  }

  def close()
}
