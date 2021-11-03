package eem.domain

import cats.syntax.apply._
import io.dylemma.spac._
import io.dylemma.spac.xml._

final case class PersonName(first: String, last: String)

object PersonName {
  implicit val personParser: XmlParser[PersonName] = (
    Splitter.xml(* \ "First").text.parseFirst,
    Splitter.xml(* \ "Last").text.parseFirst
  ).mapN(PersonName.apply)
}
