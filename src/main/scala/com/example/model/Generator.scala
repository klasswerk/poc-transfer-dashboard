package com.example.model

import scala.collection.mutable

/**
  * Simple generator to create some events.
  */
object Generator {

  // Seed explicitly so the same random sequence is returned
  val random = new scala.util.Random(123321)
  var eventId = 1

  case class PartnerMetaInfo(partner: Partner, percentSendVsReceive: Int, isSync: Boolean)

  trait PartnerMetaInfoSupplier {
    def getPartnerMetaInfo: Seq[PartnerMetaInfo]
  }

  /*
  Create a simple supplier.  This can be swapped out
  to some other implementation if needed.
   */
  object SamplePartners extends PartnerMetaInfoSupplier {

    def getPartnerMetaInfo: Seq[PartnerMetaInfo] = {

      List(
        PartnerMetaInfo(Partner("SEA"), 50, isSync = true),
        PartnerMetaInfo(Partner("LAX"), 50, isSync = false),
        PartnerMetaInfo(Partner("YYZ"), 40, isSync = false),
        PartnerMetaInfo(Partner("CDG"), 60, isSync = false),
        PartnerMetaInfo(Partner("LHR"), 10, isSync = false),
        PartnerMetaInfo(Partner("JFK"), 90, isSync = true)
      )
    }
  }

  val partners: Array[PartnerMetaInfo] = SamplePartners.getPartnerMetaInfo.toArray

  def getRandomPartnerMetaInfo: PartnerMetaInfo = {
    partners(random.nextInt(partners.length))
  }

  def getRandomEvent(timeStamp: TimeStamp): Event = {

    val partnerInfo = getRandomPartnerMetaInfo

    val id = eventId.toString
    eventId = eventId + 1

    // TODO: Add Errors
    val sendOrReceive = if (random.nextInt(101) <= partnerInfo.percentSendVsReceive) Send else Receive

    val size = 1024 + random.nextInt(200000)

    val ackState = if (partnerInfo.isSync) None else Some(AckPending)

    Event(
      EventId(id),
      partnerInfo.partner,
      timeStamp,
      sendOrReceive,
      size,
      ackState,
      None
    )
  }
  
  // TODO: look into fold rather than looping

  def generateEvents(start: TimeStamp, end: TimeStamp)(f: Event => Unit): Unit = {


    var currentDt = start.dt
    val endDt = end.dt

    implicit val order: Ordering[Event] = OrderingByTimeEvent

    val pq: mutable.PriorityQueue[Event] = new mutable.PriorityQueue[Event]()

    // Any events in priority queue to catch up
    var head:Option[Event] = pq.headOption

    while (currentDt.isBefore(endDt)) {

      head = pq.headOption
      while(head.isDefined && !head.get.timestamp.dt.isAfter(currentDt)) {
        f(pq.dequeue())
        head = pq.headOption
      }

      // New event
      val event = getRandomEvent(TimeStamp(currentDt))

      event match {
        case s@Event(_, _, _, Send, _, Some(AckPending), None) =>
          val newT = s.timestamp.dt.plusSeconds(5 + random.nextInt(300))
          val ts = TimeStamp(newT)
          val ack = Event(EventId("r" + s.eventId.id), s.partner, ts, Receive, 100, Some(AckReceived), Some(s.eventId))
          pq += ack
        case r@Event(_, _, _, Receive, _, Some(AckPending), None) =>
          val newT = r.timestamp.dt.plusSeconds(5 + random.nextInt(300))
          val ts = TimeStamp(newT)
          val ack = Event(EventId("s" + r.eventId.id), r.partner, ts, Send, 100, Some(AckReceived), Some(r.eventId))
          pq += ack
        case _ =>
      }

      f(event)

      // Advance time
      val seconds = 1 + random.nextInt(3)
      currentDt = currentDt.plusSeconds(seconds)
    }

    head = pq.headOption
    while(head.isDefined) {
      f(pq.dequeue())
      head = pq.headOption
    }
  }
}
