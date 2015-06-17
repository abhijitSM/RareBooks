package com.rarebooks.library

import akka.actor.Actor.Receive
import akka.actor._
import com.rarebooks.library.RareBooksProtocol.Msg

/**
 * Created by abhijitsingh on 17/06/15.
 */
class RareBooks extends Actor with ActorLogging with Stash {

  import context.dispatcher
  import scala.concurrent.duration._
  import RareBooks._

  private val openDuration: FiniteDuration = 5 minute
  private val closeDuration: FiniteDuration = 3 minute
  private val findBookDuration: FiniteDuration = 3 minute

  private val librarian = createLibrarian()

  private var requestsToday: Int = 0
  private var totalRequests: Int = 0

  context.system.scheduler.scheduleOnce(openDuration, self, Close)

  override def receive: Receive = open

  private def open: Receive = {
    case m: Msg =>
      librarian forward (m)
      requestsToday += 1
    case Close =>
      context.system.scheduler.scheduleOnce(closeDuration, self, Open)
      log.info(s"shop closing")
      context.become(close)
      self ! Report
  }

  def close: Receive = {
    case Open =>
      context.system.scheduler.scheduleOnce(openDuration, self, Close)
      log.info("Opening shop")
      unstashAll()
      context.become(open)
    case Report =>
      totalRequests += requestsToday
      log.info(s"$requestsToday....requests processed = $totalRequests")
      requestsToday = 0
    case _ => stash()
  }

  private def createLibrarian(): ActorRef = {
<<<<<<< HEAD
//    context.actorOf(Librarian.props(findBookDuration), "librarian")
    _
=======
    context.actorOf(Librarian.props(findBookDuration), "librarian")
>>>>>>> Initial Commit
  }

}

object RareBooks {

  case object Open

  case object Close

  case object Report

  def props = Props(new RareBooks)

}
