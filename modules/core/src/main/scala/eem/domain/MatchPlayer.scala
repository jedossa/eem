package eem.domain

import cats.syntax.apply._
import io.dylemma.spac._
import io.dylemma.spac.xml._

final case class MatchPlayer(playerRef: String, stats: List[Stat])

object MatchPlayer {
  implicit val matchPlayerParser: XmlParser[MatchPlayer] = (
    XmlParser.attr("PlayerRef"),
    Splitter.xml(* \ "Stat").as[Stat].parseToList
  ).mapN(MatchPlayer.apply)
}
