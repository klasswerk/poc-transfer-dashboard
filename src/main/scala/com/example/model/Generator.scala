package com.example.model

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
        PartnerMetaInfo(Partner("SEA"), 50, true),
        PartnerMetaInfo(Partner("LAX"), 50, false),
        PartnerMetaInfo(Partner("YYZ"), 40, false),
        PartnerMetaInfo(Partner("CDG"), 60, false),
        PartnerMetaInfo(Partner("LHR"), 10, false),
        PartnerMetaInfo(Partner("JFK"), 90, true)
      )
    }
  }

  val partners = SamplePartners.getPartnerMetaInfo.toArray

  def getRandomPartnerMetaInfo = {
    partners(random.nextInt(partners.length))
  }

  def getRandomEvent(timeStamp: TimeStamp): Event = {

    val partnerInfo = getRandomPartnerMetaInfo

    val id = eventId.toString
    eventId = eventId + 1

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

  // TODO: need to fix timestamps first.

  def generateEvents(start: TimeStamp, end: TimeStamp) = {

  }
}
