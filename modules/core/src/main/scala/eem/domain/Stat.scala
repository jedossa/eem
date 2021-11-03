package eem.domain

import cats.syntax.apply._
import io.dylemma.spac._
import io.dylemma.spac.xml._

final case class Stat(`type`: String, value: Int)

object Stat {
  implicit val statParser: XmlParser[Stat] = (
    XmlParser.attr("Type"),
    Splitter.xml("Stat").text.map(_.toInt).parseFirst
  ).mapN(Stat.apply)
}
