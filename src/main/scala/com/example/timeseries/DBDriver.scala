package com.example.timeseries

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Generic TimeSeries Database driver
  */
trait DBDriver {

  /**
    * Insert timeseries point into database.
    */
  def insertPoint(timeSeriesPoint: TimeSeriesPoint) : Future[Boolean]

  /**
    * Insert a seq of timeseries points into the database.
    *
    * This is a default implementation which might make as many network
    * trips as the length of the seq.  Some databases migth have a more
    * efficient bulk write method and those implementations shouuld
    * override this method.
    */
  def insertPoints(timeSeriesPoints: Seq[TimeSeriesPoint]) : Future[Boolean] = {

    if (timeSeriesPoints.length == 1)
      insertPoint(timeSeriesPoints.head)
    else {
      val future_seq = timeSeriesPoints.map(insertPoint)
      Future.sequence(future_seq).map(_.reduce { _ && _ })
    }
  }

  /**
    * Close database.
    */
  def close()
}
