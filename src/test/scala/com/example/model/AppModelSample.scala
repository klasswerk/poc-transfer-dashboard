package com.example.model

import org.joda.time.DateTime

/**
  * Simple example to illustrate events
  */
object AppModelSample extends App {


  val syncSend = Event(EventId("1"), Partner("abc"), TimeStamp(DateTime.now), Send, 1024, 5, None, None)

  val asyncSend = Event(EventId("2"), Partner("abc"), TimeStamp(DateTime.now), Send, 65536, 15, Some(AckPending), None)
  val receiptReceive = Event(EventId("3"), Partner("abc"), TimeStamp(DateTime.now), Receive, 20480, 7, None, Some(asyncSend.eventId))

  println("sync send = [" + syncSend + "]")
  println("async send = [" + asyncSend + "]")
  println("receipt received = [" + receiptReceive + "]")
}
