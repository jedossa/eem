package eem.domain

import cats.syntax.apply._
import io.dylemma.spac._
import io.dylemma.spac.xml._

final case class SoccerDocument(matchData: MatchData, teams: List[Team])

object SoccerDocument {
  val transformer: XmlParser[SoccerDocument] = (
    Splitter.xml("SoccerFeed" \ "SoccerDocument" \ "MatchData").as[MatchData].parseFirst,
    Splitter.xml("SoccerFeed" \ "SoccerDocument" \ "Team").as[Team].parseToList
  ).mapN(SoccerDocument.apply)
}
