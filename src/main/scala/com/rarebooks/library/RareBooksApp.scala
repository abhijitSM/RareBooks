package com.rarebooks.library

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
 * Created by abhijitsingh on 19/06/15.
 */
object RareBooksApp extends App {
  val config = ConfigFactory.load()
  val system = ActorSystem()
  val rareBooks = system.actorOf(RareBooks.props, "rareBooks")
  val customer = system.actorOf(Customer.props(
    rareBooks,
    config.getInt("rareBooks.customer.odds"),
    config.getInt("rareBooks.customer.tolerance")
  ))
}
