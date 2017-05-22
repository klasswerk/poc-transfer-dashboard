package com.example.model

/**
  * Simple example to illustrate events
  */
object AppModelSample extends App {


  val syncSend = Event(EventId("1"), Partner("abc"), TimeStamp(""), Send, 1024, None, None)

  val asyncSend = Event(EventId("2"), Partner("abc"), TimeStamp(""), Send, 65536, Some(AckPending), None)
  val receiptReceive = Event(EventId("3"), Partner("abc"), TimeStamp(""), Receive, 20480, None, Some(asyncSend.id))

  println("sync send = [" + syncSend + "]")
  println("async send = [" + asyncSend + "]")
  println("receipt received = [" + receiptReceive + "]")
}
