package com.rarebooks.library

import scala.compat.Platform

/**
 * Created by abhijitsingh on 17/06/15.
 * This file contains the messages for the rare books info service.
 */
object RareBooksProtocol {

  sealed trait Topic

  case object Africa extends Topic

  case object Asia extends Topic

  case object Greece extends Topic

  case object Persia extends Topic

  case object Royalty extends Topic

  case object Philosophy extends Topic

  case object Gilgamesh extends Topic

  case object Unknown extends Topic

  /**
   * Card trait for book cards.
   */
  sealed trait Card {
    def title: String

    def description: String

    def topic: Set[Topic]
  }

  /**
   * Book card class.
   *
   * @param isbn the book isbn
   * @param author the book author
   * @param title the book title
   * @param description the book description
   * @param dateOfOrigin the book date of origin
   * @param topic set of associated tags for the book
   * @param publisher the book publisher
   * @param language the language the book is in
   * @param pages the number of pages in the book
   */
  final case class BookCard(
                             isbn: String,
                             author: String,
                             title: String,
                             description: String,
                             dateOfOrigin: String,
                             topic: Set[Topic],
                             publisher: String,
                             language: String,
                             pages: Int)
    extends Card

  /** trait for all messages. */
  trait Msg {
    def dateInMillis: Long
  }

  /**
   * Find book by isbn message.
   *
   * @param isbn isbn to search for
   * @param dateInMillis date message was created
   */
  final case class FindBookByIsbn(
                                   isbn: String,
                                   dateInMillis: Long = Platform.currentTime
                                   ) extends Msg {
    require(isbn.nonEmpty, "Isbn Required")
  }

  /**
   * Find book by topic.
   * * @param topic set of topics to search for
   * @param dateInMillis date message was created
   */
  final case class FindBookByTopic(
                                    topic: Set[Topic],
                                    dateInMillis: Long = Platform.currentTime) extends Msg {
    require(topic.nonEmpty, "Topic required.")
  }

  final case class FindBookByTitle(
                                    title: String,
                                    dateInMillis: Long = Platform.currentTime) extends Msg {
    require(title.nonEmpty, "Topic required.")
  }

  final case class BookFound(
                              bookCards: List[BookCard],
                              dateInMillis: Long = Platform.currentTime) extends Msg

  final case class BookNotFound(
                                 msg: String,
                                 dateInMillis: Long = Platform.currentTime) extends Msg

  final case class Complain(
                             dateInMillis: Long = Platform.currentTime) extends Msg

  final case class Credit(
                           dateInMillis: Long = Platform.currentTime) extends Msg

  final case class GetCustomer(
                                dateInMillis: Long = Platform.currentTime) extends Msg

}
