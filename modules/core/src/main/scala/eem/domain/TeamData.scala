package eem.domain

import cats.syntax.apply._
import io.dylemma.spac._
import io.dylemma.spac.xml._

final case class TeamData(side: String, teamRef: String, playerLineUp: PlayerLineUp, teamStats: List[TeamStat])

object TeamData {
  implicit val teamDataParser: XmlParser[TeamData] = (
    XmlParser.attr("Side"),
    XmlParser.attr("TeamRef"),
    Splitter.xml(* \ "PlayerLineUp").as[PlayerLineUp].parseFirst,
    Splitter.xml(* \ "Stat").as[TeamStat].parseToList
  ).mapN(TeamData.apply)
}
