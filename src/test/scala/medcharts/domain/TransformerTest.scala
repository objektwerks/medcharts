package medcharts.domain

import medcharts.domain.Logger._
import medcharts.domain.Transformer._

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.util.{Failure, Success}

class TransformerTest extends AnyFunSuite with Matchers {
  test("glucose") {
    testGlucosesTransformer("./data/glucose/glucose.txt", 18, 0)
    testGlucosesTransformer("./data/glucose/glucose-invalid.txt", 16, 2)
 }

  test("meds") {
    testMedsTransformer("./data/meds/meds.txt", 18, 0)
    testMedsTransformer("./data/meds/meds-invalid.txt", 16, 2)
  }

  private def testGlucosesTransformer(path: String, linesCount: Int, invalidLinesCount: Int): Unit = {
    transform[Glucoses](path) match {
      case Success(glucoses) => 
        glucoses.lines.length shouldEqual linesCount
        glucoses.invalidLines.length shouldEqual invalidLinesCount
      case Failure(failure) =>
        logIOFailure(failure, path)
        fail
    }
    ()
  }

  private def testMedsTransformer(path: String, linesCount: Int, invalidLinesCount: Int): Unit = {
    transform[Meds](path) match {
      case Success(meds) => 
        meds.lines.length shouldEqual linesCount
        meds.invalidLines.length shouldEqual invalidLinesCount
      case Failure(failure) =>
        logIOFailure(failure, path)
        fail
    }
    ()
  }
}