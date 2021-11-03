package eem.domain

import io.dylemma.spac._
import io.dylemma.spac.xml._

final case class MatchData(teamsData: List[TeamData])

object MatchData {
  implicit val matchDataParser: XmlParser[MatchData] =
    Splitter.xml(* \ "TeamData").as[TeamData].parseToList.map(MatchData.apply)
}
