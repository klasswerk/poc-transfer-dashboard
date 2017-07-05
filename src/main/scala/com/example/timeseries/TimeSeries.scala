package com.example.timeseries

import java.util.concurrent.TimeUnit

/**
  * Generic TimeSeriesPoint
  */
trait TimeSeriesPoint {

  def getMeasurement: String

  def getTags: Option[Seq[(String, Any)]]
  def getFields: Seq[(String, Any)]

  def getTimeStamp: Option[Long]
  def getPrecision: TimeUnit

}

/**
  * Explicit TimeSeriesPoint, which can be used for ad-hoc
  * purposes.
  */
case class ExplicitTimeSeriesPoint(
             measurement: String,
             tags: Option[Seq[(String, Any)]],
             fields: Seq[(String, Any)],
             timeStamp: Option[Long],
             precision: TimeUnit) extends TimeSeriesPoint {

  override def getMeasurement: String = measurement

  override def getTags: Option[Seq[(String, Any)]] = tags

  override def getFields: Seq[(String, Any)] = fields

  override def getTimeStamp: Option[Long] = timeStamp

  override def getPrecision: TimeUnit = precision
}