package com.example.publisher

import akka.actor.{Actor, ActorSystem, Cancellable, Props}

import scala.concurrent.duration._

/**
  * Publisher to process repeated actions
  */
class Publisher(f: Any => Unit) {

  val system = ActorSystem("Publisher")

  object Publish;

  class PublishActor(p : Any => Unit) extends Actor {

    def receive = {
      case Publish => f()
    }
  }

  val publishActor = system.actorOf(Props(new PublishActor(f)))

  def start : Cancellable = {
    import system.dispatcher
    system.scheduler.schedule(
      0 seconds,
      15 seconds,
      publishActor,
      Publish
    )
  }

  def stop(cancellable: Cancellable) : Unit = {
    cancellable.cancel()
  }

}
