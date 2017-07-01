package com.example.model

import org.joda.time.DateTime

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by ep on 6/30/17.
  */
object AppLoaderSample extends App {

  implicit def dt2TimeStamp(dt: DateTime): TimeStamp = {
    TimeStamp(dt)
  }

  val event = Generator.getRandomEvent(DateTime.now)

  println(event)
  println(event.partner)

  val insert = Loader.loadEvent(event)

  Await.result (insert, 10 second)
  insert.value match {
    case Some (Success(_) ) => println ("Success called")
    case Some (Failure(e) ) => println ("Insert into kafka failed: " + e)
    case None => println ("Future not done")
  }
}
