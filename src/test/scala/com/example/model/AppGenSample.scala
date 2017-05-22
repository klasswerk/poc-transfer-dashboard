package com.example.model

/**
  * Simple example to illustrate events
  */
object AppGenSample extends App {

  println("random partner info = [" + Generator.getRandomPartnerMetaInfo() + "]")
  println("random event = [" + Generator.getRandomEvent(TimeStamp("100")) + "]")
  println("random event = [" + Generator.getRandomEvent(TimeStamp("101")) + "]")
  println("random event = [" + Generator.getRandomEvent(TimeStamp("102")) + "]")
  println("random event = [" + Generator.getRandomEvent(TimeStamp("103")) + "]")
  println("random event = [" + Generator.getRandomEvent(TimeStamp("104")) + "]")
}
