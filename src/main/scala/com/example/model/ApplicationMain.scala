package com.example.model

/**
  * Simple example to illustrate events
  */
object ApplicationMain extends App {


  val syncSend = Event(EventId("1"), Partner("abc"), TimeStamp(""), Send, None, None)

  val asyncSend = Event(EventId("2"), Partner("abc"), TimeStamp(""), Send, Some(AckPending), None)

  val receiptReceive = Event(EventId("3"), Partner("abc"), TimeStamp(""), Receive, None, Some(asyncSend.id))

  println("sync send = [" + syncSend + "]")
  println("async send = [" + asyncSend + "]")
  println("receipt received = [" + receiptReceive + "]")
}
