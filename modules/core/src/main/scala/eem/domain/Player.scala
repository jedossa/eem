package eem.domain

import cats.syntax.apply._
import io.dylemma.spac._
import io.dylemma.spac.xml._

final case class Player(uID: String, personName: PersonName)

object Player {
  implicit val playerParser: XmlParser[Player] = (
    XmlParser.attr("uID"),
    Splitter.xml(* \ "PersonName").as[PersonName].parseFirst
  ).mapN(Player.apply)
}
