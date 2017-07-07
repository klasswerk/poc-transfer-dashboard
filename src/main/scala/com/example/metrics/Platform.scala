package com.example.metrics

import java.lang.management.ManagementFactory

/**
  * Simple measurements from MXBeans
  */
object Platform {

  val threadMXBean = ManagementFactory.getThreadMXBean
  val memoryMXBean = ManagementFactory.getMemoryMXBean

  def getThreadCount : Option[Int] = {
    Some(threadMXBean.getThreadCount)
  }

  def getHeapMemory : Option[Long] = {
    Some(memoryMXBean.getHeapMemoryUsage.getUsed)
  }

  def getPendingFinalization : Option[Int] = {
    Some(memoryMXBean.getObjectPendingFinalizationCount)
  }
}
