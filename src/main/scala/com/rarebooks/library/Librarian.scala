package com.rarebooks.library

import akka.actor._
import com.rarebooks.library.RareBooksProtocol.{BookFound, BookNotFound}

import scala.concurrent.duration.FiniteDuration

/**
 * Created by abhijitsingh on 18/06/15.
 */
class Librarian(findBookDuration: FiniteDuration) extends Actor with ActorLogging with Stash {

  import context.dispatcher
  import Librarian._
  import RareBooksProtocol._

  override def receive: Receive = ready

  def ready: Receive = {
    case c: Complain =>
      //      sender() ! credit
      log.info(s"Credit issued to customer : $sender()")
    case f: FindBookByIsbn =>
    case f: FindBookByTitle =>
    case f: FindBookByTopic => research(Done(findByTopic(f), sender()))
  }

  def busy: Receive = {
    case Done(e, s) =>
      process(e)
      unstashAll()
      context.become(ready)

    case _ => stash()


  }

  def research(d: Done): Unit = {
    context.system.scheduler.scheduleOnce(findBookDuration, self, d)
    context.become(busy)
  }

  def process(r: Either[BookNotFound, BookFound]): Unit = {
    r fold(
      n => {
        sender() ! n
        log.info(n.toString)
      },
      f => sender() ! f
      )
  }
}

object Librarian {

  import RareBooksProtocol._
  import Catalog._

  final case class Done(
                         e: Either[BookNotFound, BookFound],
                         customer: ActorRef)

  def props(findBookDuration: FiniteDuration): Props =
    Props(new Librarian(findBookDuration))


  private def optToEither[T](
                              v: T,
                              f: T => Option[List[BookCard]]): Either[BookNotFound, BookFound] =
    f(v) match {
      case b: Some[List[BookCard]] =>
        Right(BookFound(b.get))
      case _ =>
        Left(BookNotFound(s"Book(s) not found based on $v"))
    }

  private def findByTitle(fb: FindBookByTitle) =
    optToEither[String](fb.title, findBookByTitle)

  private def findByTopic(fb: FindBookByTopic) =
    optToEither[Set[Topic]](fb.topic, findBookByTopic)
}