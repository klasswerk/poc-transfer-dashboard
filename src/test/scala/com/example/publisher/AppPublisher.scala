package com.example.publisher

/**
  *
  */
object AppPublisher extends App {

  new Publisher(_ => println("Publishing")).start

}

