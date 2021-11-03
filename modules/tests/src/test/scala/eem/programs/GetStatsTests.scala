package eem.programs

import cats.effect.IO
import eem.programs.GetStats.{PlayerStat, TeamStatSummary, TeamStats}
import weaver.SimpleIOSuite
import weaver.scalacheck.Checkers

import java.io.File

// TODO add tests for unhappy paths
object GetStatsTests extends SimpleIOSuite with Checkers {

  val file = new File("src/resources/1-F9-2192085691-srml-8-2017-f919230-matchresults-1 (1) (1).xml")

  test("get stats for left side passes") {
    GetStats
      .apply(file, "leftside_pass")
      .run
      .map(
        summary =>
          expect.same(
            List(
              TeamStatSummary(
                TeamStats(teamSide = "Home", teamName = "Swansea City", stats = "56"),
                List(
                  PlayerStat(rank = 1, firstName = "Tom", lastName = "Carroll", stat = 10),
                  PlayerStat(rank = 2, firstName = "Kyle", lastName = "Naughton", stat = 9),
                  PlayerStat(rank = 3, firstName = "Sung-yueng", lastName = "Ki", stat = 7),
                  PlayerStat(rank = 4, firstName = "Andy", lastName = "King", stat = 7),
                  PlayerStat(rank = 5, firstName = "Federico", lastName = "Fernandez", stat = 6)
                )
              ),
              TeamStatSummary(
                TeamStats(teamSide = "Away", teamName = "Everton", stats = "55"),
                List(
                  PlayerStat(rank = 1, firstName = "Phil", lastName = "Jagielka", stat = 10),
                  PlayerStat(rank = 2, firstName = "Morgan", lastName = "Schneiderlin", stat = 10),
                  PlayerStat(rank = 3, firstName = "Seamus", lastName = "Coleman", stat = 7),
                  PlayerStat(rank = 4, firstName = "Michael", lastName = "Keane", stat = 6),
                  PlayerStat(rank = 5, firstName = "Idrissa", lastName = "Gueye", stat = 6)
                )
              )
            ),
            summary
          )
      )
      .compile
      .lastOrError
      .to[IO]
  }

  test("get stats for saves") {
    GetStats
      .apply(file, "saves")
      .run
      .map(
        summary =>
          expect.same(
            List(
              TeamStatSummary(
                TeamStats(teamSide = "Home", teamName = "Swansea City", stats = "1"),
                List(
                  PlayerStat(rank = 1, firstName = "Lukasz", lastName = "Fabianski", stat = 1),
                  PlayerStat(rank = 2, firstName = "Federico", lastName = "Fernandez", stat = 0),
                  PlayerStat(rank = 3, firstName = "Martin", lastName = "Olsson", stat = 0),
                  PlayerStat(rank = 4, firstName = "Alfie", lastName = "Mawson", stat = 0),
                  PlayerStat(rank = 5, firstName = "Kyle", lastName = "Naughton", stat = 0)
                )
              ),
              TeamStatSummary(
                TeamStats(teamSide = "Away", teamName = "Everton", stats = "2"),
                List(
                  PlayerStat(rank = 1, firstName = "Jordan", lastName = "Pickford", stat = 2),
                  PlayerStat(rank = 2, firstName = "Phil", lastName = "Jagielka", stat = 0),
                  PlayerStat(rank = 3, firstName = "Leighton", lastName = "Baines", stat = 0),
                  PlayerStat(rank = 4, firstName = "Seamus", lastName = "Coleman", stat = 0),
                  PlayerStat(rank = 5, firstName = "Michael", lastName = "Keane", stat = 0)
                )
              )
            ),
            summary
          )
      )
      .compile
      .lastOrError
      .to[IO]
  }

  // TODO get an error instead
  test("get stats for invalid type of stat") {
    GetStats
      .apply(file, "???")
      .run
      .map(
        summary =>
          expect.same(
            List(
              TeamStatSummary(
                TeamStats(teamSide = "Home", teamName = "Swansea City", stats = ""),
                List(
                  PlayerStat(rank = 1, firstName = "Lukasz", lastName = "Fabianski", stat = 0),
                  PlayerStat(rank = 2, firstName = "Federico", lastName = "Fernandez", stat = 0),
                  PlayerStat(rank = 3, firstName = "Martin", lastName = "Olsson", stat = 0),
                  PlayerStat(rank = 4, firstName = "Alfie", lastName = "Mawson", stat = 0),
                  PlayerStat(rank = 5, firstName = "Kyle", lastName = "Naughton", stat = 0)
                )
              ),
              TeamStatSummary(
                TeamStats(teamSide = "Away", teamName = "Everton", stats = ""),
                List(
                  PlayerStat(rank = 1, firstName = "Jordan", lastName = "Pickford", stat = 0),
                  PlayerStat(rank = 2, firstName = "Phil", lastName = "Jagielka", stat = 0),
                  PlayerStat(rank = 3, firstName = "Leighton", lastName = "Baines", stat = 0),
                  PlayerStat(rank = 4, firstName = "Seamus", lastName = "Coleman", stat = 0),
                  PlayerStat(rank = 5, firstName = "Michael", lastName = "Keane", stat = 0)
                )
              )
            ),
            summary
          )
      )
      .compile
      .lastOrError
      .to[IO]
  }
}
