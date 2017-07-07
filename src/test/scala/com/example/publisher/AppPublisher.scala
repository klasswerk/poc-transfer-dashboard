package com.example.publisher

import com.example.metrics.MetricsPublisher

/**
  *
  */
object AppPublisher extends App {

  // new Publisher(MetricsPublisher.pubishMetrics).start
  new Publisher(() => println("Called")).start

}

