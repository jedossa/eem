package eem.domain

import cats.syntax.apply._
import io.dylemma.spac._
import io.dylemma.spac.xml._

final case class Team(uID: String, name: String, players: List[Player])

object Team {
  implicit val teamParser: XmlParser[Team] = (
    XmlParser.attr("uID"),
    Splitter.xml(* \ "Name").text.parseFirst,
    Splitter.xml(* \ "Player").as[Player].parseToList
  ).mapN(Team.apply)
}
