package eem.programs

import cats.effect.SyncIO
import cats.syntax.eq._
import eem.domain.{ SoccerDocument, Team }
import eem.programs.GetStats._
import fs2.Stream
import io.dylemma.spac.xml._

import java.io.File

final case class GetStats[F[_]] private (file: File, statType: String) {

  // TODO handle missing fields properly
  def run: Stream[SyncIO, List[TeamStatSummary]] = {
    JavaxSource
      .syncIO(file)
      .through(SoccerDocument.transformer.toPipe)
      .map { soccerDocument =>
        soccerDocument.matchData.teamsData.map { teamData =>
          val team = getTeamByID(soccerDocument, teamData.teamRef)

          val sumOfStats = teamData.teamStats.find(_.`type` === statType).fold("")(_.value)

          val topFive = teamData.playerLineUp.matchPlayers
            .map { player =>
              player.playerRef -> player.stats.find(_.`type` === statType).fold(0)(_.value)
            }
            .sortWith { case ((_, statA), (_, statB)) => statB < statA }
            .take(5)
            .zipWithIndex
            .flatMap {
              case ((playerUID, stat), rank) =>
                team.flatMap(
                  _.players
                    .find(_.uID === playerUID)
                    .map(player => PlayerStat(rank + 1, player.personName.first, player.personName.last, stat))
                )
            }

          TeamStatSummary(TeamStats(teamData.side, team.map(_.name).getOrElse(""), sumOfStats), topFive)
        }
      }
  }
}

object GetStats {

  def getTeamByID(soccerDocument: SoccerDocument, teamUID: String): Option[Team] =
    soccerDocument.teams
      .foldLeft(Map.empty[String, Team])((m, s) => m + (s.uID -> s))
      .get(teamUID)

  final case class TeamStats(teamSide: String, teamName: String, stats: String)
  object TeamStats {
    def show(teamStats: TeamStats): String =
      s"${teamStats.teamSide}; ${teamStats.teamName} - ${teamStats.stats}"
  }

  final case class PlayerStat(rank: Int, firstName: String, lastName: String, stat: Int)
  object PlayerStat {
    def show(playerStat: PlayerStat): String =
      s"${playerStat.rank.toString}. ${playerStat.firstName} ${playerStat.lastName} - ${playerStat.stat.toString}"
  }

  final case class TeamStatSummary(teamStats: TeamStats, topPlayers: List[PlayerStat])
  object TeamStatSummary {
    def show(teamStatSummary: TeamStatSummary): String =
      s"""${TeamStats.show(teamStatSummary.teamStats)}
         |${teamStatSummary.topPlayers.map(PlayerStat.show).mkString("\n")}
         |""".stripMargin
  }
}
