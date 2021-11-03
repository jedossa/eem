package eem.domain

import io.dylemma.spac._
import io.dylemma.spac.xml._

final case class PlayerLineUp(matchPlayers: List[MatchPlayer])

object PlayerLineUp {
  implicit val playerLineUpParser: XmlParser[PlayerLineUp] =
    Splitter.xml(* \ "MatchPlayer").as[MatchPlayer].parseToList.map(PlayerLineUp.apply)
}
