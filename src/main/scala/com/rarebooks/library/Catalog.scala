package com.rarebooks.library

/**
 * Created by abhijitsingh on 17/06/15.
 */
object Catalog {

  import RareBooksProtocol._

  val phaedrus = BookCard(
    "0872202208",
    "Plato",
    "Phaedrus",
    "Plato's enigmatic text that treats a range of important ... issues.",
    "370 BC",
    Set(Greece, Philosophy),
    "Hackett Publishing Company, Inc.",
    "English",
    144)
  val theEpicOfGilgamesh = BookCard(
    "0141026286",
    "unknown",
    "The Epic of Gilgamesh",
    "A hero is created by the gods to challenge the arrogant King Gilgamesh.",
    "2700 BC",
    Set(Gilgamesh, Persia, Royalty),
    "Penguin Classics",
    "English",
    80)

  private val books: Map[String, BookCard] = Map(
    theEpicOfGilgamesh.isbn -> theEpicOfGilgamesh,
    phaedrus.isbn -> phaedrus)

  def findBookByTitle(title: String): Option[List[BookCard]] = {
    Option(books.filter(_._2.title.contains(title)) map (_._2) toList)
  }

  def findBookByTopic(topic: Set[Topic]): Option[List[BookCard]] = {
    Option(books.filter(bks => topic.subsetOf(bks._2.topic)) map (_._2) toList)
  }
}
