package com.example.timeseries

import scala.concurrent.Future

/**
  * Generic TimeSeries Database driver
  */
trait DBDriver {

  def insertPoint(timeSeriesPoint: TimeSeriesPoint) : Future[Boolean]
  def close()
}
