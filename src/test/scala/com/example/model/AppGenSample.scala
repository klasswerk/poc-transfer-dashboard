package com.example.model

/**
  * Simple example to illustrate events
  */
object AppGenSample extends App {

  implicit def str2TimeStamp(x: String) : TimeStamp = {
    TimeStamp(x)
  }

  println("random partner info = [" + Generator.getRandomPartnerMetaInfo + "]")
  println("random event = [" + Generator.getRandomEvent("100") + "]")
  println("random event = [" + Generator.getRandomEvent("101") + "]")
  println("random event = [" + Generator.getRandomEvent("102") + "]")
  println("random event = [" + Generator.getRandomEvent("103") + "]")
  println("random event = [" + Generator.getRandomEvent("104") + "]")
}
