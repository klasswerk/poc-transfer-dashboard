package com.example.timeseries

import java.util.concurrent.TimeUnit

import com.paulgoldbaum.influxdbclient.Parameter.Precision
import com.paulgoldbaum.influxdbclient.Parameter.Precision.Precision
import com.paulgoldbaum.influxdbclient.{Database, InfluxDB, Point}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  *
  */
object InfluxDBDriver {

  def apply(host: String, port: Int, dbname: String) = {
    new InfluxDBDriver(host, port, dbname)
  }

  /*
  Build InfluxDB Point from generic TimeSeriesPoint.
  Might want to reduce scope.  Public now for testing.
   */
  def buildPoint(tsPoint: TimeSeriesPoint) : Point = {

    val point =
      if (tsPoint.getTimeStamp.isDefined)
        Point(tsPoint.getMeasurement, tsPoint.getTimeStamp.get)
      else
        Point(tsPoint.getMeasurement)

    // Map optional tag fields
    val pointWithTags =
      if (tsPoint.getTags.isDefined) {
        val tags = tsPoint.getTags.get
        tags.foldLeft(point)((p: Point, tp: (String, Any)) =>
          tp._2 match {
            case s: String => p.addTag(tp._1, s)
            case _ => throw new RuntimeException("Wrong type for tag")
          })
      }
      else point

    // Map fields
    val fields = tsPoint.getFields
    fields.foldLeft(pointWithTags)((p: Point, fp: (String, Any)) =>
      fp._2 match {
        case s: String => p.addField(fp._1, s)
        case d: Double => p.addField(fp._1, d)
        case l: Long => p.addField(fp._1, l)
        case b: Boolean => p.addField(fp._1, b)
        case _ => throw new RuntimeException("Wrong type for field")
      })
  }

  /*
  Build InfluxDB Precision from generic TimeSeriesPoint.
  Might want to reduce scope.  Public now for testing.
   */
  def buildPrecision(tsPoint: TimeSeriesPoint): Precision = {

    tsPoint.getPrecision match {
      case TimeUnit.HOURS        => Precision.HOURS
      case TimeUnit.MINUTES      => Precision.MINUTES
      case TimeUnit.SECONDS      => Precision.SECONDS
      case TimeUnit.MILLISECONDS => Precision.MILLISECONDS
      case TimeUnit.MICROSECONDS => Precision.MICROSECONDS
      case TimeUnit.NANOSECONDS  => Precision.NANOSECONDS
      case _ => throw new RuntimeException("Unsupported precision")
    }
  }
}

/**
  * Class representing a connection to an InfluxDB  database.
  */
class InfluxDBDriver(host: String, port: Int, dbname: String) extends DBDriver {

  private val influxdb = InfluxDB.connect(host, port)
  private val db = influxdb.selectDatabase(dbname)

  val db_future: Future[Database] = db.exists().map(x =>
    if (x)
      Future.successful(db)
    else {
      val c_f = db.create()
      for (
        done <- c_f
      ) yield {
        db
      }
    }).flatMap(identity)

  override def insertPoint(tsPoint: TimeSeriesPoint): Future[Boolean] = {

    db_future.map(_.write(InfluxDBDriver.buildPoint(tsPoint),
                                 precision = InfluxDBDriver.buildPrecision(tsPoint))).flatMap(identity)
  }

  override def close: Unit = {
    influxdb.close()
  }
}
