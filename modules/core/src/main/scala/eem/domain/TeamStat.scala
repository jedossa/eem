package eem.domain

import cats.syntax.apply._
import io.dylemma.spac._
import io.dylemma.spac.xml._

final case class TeamStat(`type`: String, value: String)

object TeamStat {
  implicit val teamStatsParser: XmlParser[TeamStat] = (
    XmlParser.attr("Type"),
    Splitter.xml("Stat").text.parseFirst
  ).mapN(TeamStat.apply)
}
