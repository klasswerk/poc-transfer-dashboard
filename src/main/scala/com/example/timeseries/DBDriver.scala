package com.example.timeseries

import scala.concurrent.Future

/**
  *
  */
trait DBDriver {

  def insertPoint(timeSeriesPoint: TimeSeriesPoint) : Future[Boolean]
  def close()
}
