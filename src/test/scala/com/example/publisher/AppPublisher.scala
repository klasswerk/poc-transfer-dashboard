package com.example.publisher

import com.example.metrics.MetricsPublisher

/**
  *
  */
object AppPublisher extends App {

  new Publisher(MetricsPublisher.publishMetrics).start
  // new Publisher(() => println("Called")).start

}

