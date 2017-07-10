package com.example.model

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

/**
  * Object for time manipulation in Events.
  */
object Time {

  val fmt = ISODateTimeFormat.dateTime

  def stringToDateTime(str: String) : DateTime = {
    fmt.parseDateTime(str)
  }

  def dateTimeToString(dt: DateTime) : String = {
    dt.toString(fmt)
  }
}
