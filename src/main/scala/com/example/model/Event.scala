package com.example.model

import org.joda.time.DateTime

/**
  * Event modelled after our data transfer system.
  *
  * Id, Partner, and Timestamp are standard.  Timestamp will be in ISO String format.
  *
  * The event is either a send or a receive.  Sync transfers have None for ackState and None
  * for receiptFor.  Async transfers have Some(AckPending) for ackState and None for receiptFor.
  *
  * Each async transfer needs a completing event in the opposite direction, i.e. an async send
  * needs a completing receive receipt and an async receive needs a completing send receipt.  The
  * receipts are the only events which have a Some(original_eventId) for receiptFor.
  *
  * For example:
  *
  * Sync send -> sendReceive of Send, ackState of None, receiptFor of None
  *
  * Async send -> sendReceive of Send, ackState of Some(AckPending), receiptFor of None
  * Completing Ack received -> sendReceive of Receive, ackState of None, receiptFor of Some(original_eventId)
  *
  */

case class EventId(id: String)
case class Partner(name: String)

case class TimeStamp(dt: DateTime)

sealed trait Action

object Send extends Action
object Receive extends Action
object Error extends Action

sealed trait AckState

object AckPending extends AckState
object AckReceived extends AckState

case class Event(eventId: EventId,
                 partner: Partner,
                 timestamp: TimeStamp,
                 action: Action,
                 size: Int,
                 seconds: Int,
                 ackState: Option[AckState],
                 receiptFor: Option[EventId])

object OrderingByTimeEvent extends Ordering[Event] {

  override def compare(x: Event, y: Event): Int = {

    val a = x.timestamp.dt
    val b = y.timestamp.dt
    -a.compareTo(b)
  }
}
