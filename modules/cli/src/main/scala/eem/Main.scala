package eem

import cats.effect._
import cats.syntax.eq._
import eem.programs.GetStats
import eem.programs.GetStats.TeamStatSummary
import org.typelevel.log4cats.SelfAwareStructuredLogger
import org.typelevel.log4cats.slf4j.Slf4jLogger

import java.io.File

object Main extends App {

  implicit val logger: SelfAwareStructuredLogger[IO] = Slf4jLogger.getLogger[IO]

//  val file = new File("./1-F9-2192085691-srml-8-2017-f919230-matchresults-1 (1) (1).xml")

  if (args.length === 2) {
    SyncIO(println("Type of statistic to check and path to a file to process are required"))
  }
  val statName = args(0)
  val fileName = args(1)

  GetStats
    .apply(new File(fileName), statName)
    .run
    .evalTap(
      result =>
        SyncIO(
          result.foreach(summary => println(TeamStatSummary.show(summary)))
        )
    )
    .compile
    .drain
    .unsafeRunSync()
}
