

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.IOResult
import akka.stream.scaladsl.{FileIO, Flow, Framing, Source}

import akka.util.ByteString
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext.Implicits.global
import org.apache.commons.csv.CSVFormat
import java.nio.file.Paths
import scala.concurrent.Future


object Milestone6 extends App {
  val applicationConf = ConfigFactory.load("application.conf") //loading from configuration file
  val filepath = applicationConf.getString("StoreData.filepath") //getting file path from configuration file

  object CsvFormattings {
    val RFC4180: CSVFormat = CSVFormat.DEFAULT.withRecordSeparator("\r\n").withDelimiter(',')
  }
  implicit val system = ActorSystem("FileReaderSystem")

  val categoryFilter = applicationConf.getString("StoreData.categoryFilter") //getting category filter from configuration file
  val FinancialYear = applicationConf.getString("StoreData.year") //getting category filter from configuration file
  val categorySinkFile = applicationConf.getString("StoreData.CategorySinkFile") //getting category sink file from configuration file
  val categoryWiseFinancialYearSinkFile = applicationConf.getString("StoreData.CategoryWiseFinancialYearSinkFile") //getting category sink file from configuration file


  val fileSource: Source[ByteString, Future[IOResult]] =
    FileIO.fromPath(Paths.get(filepath))


  val categoryFilterFlow: Flow[ByteString, ByteString, NotUsed] =
    Flow[ByteString]
      .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 256, allowTruncation = true))
      .filter(record => {
        val fields = record.utf8String.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)").map(_.trim)
        fields(9) == categoryFilter
      })

  val financialYearAggregatorFlow: Flow[ByteString, ByteString, NotUsed] = {

    Flow[ByteString]
      .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 256, allowTruncation = true))
      .filter(record => {

        val fields = record.utf8String.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)").map(_.trim)
        fields(1).contains(FinancialYear)
      })
      .fold((0.0, 0.0, 0.0, 0.0)) { (acc, record) =>

        val fields = record.utf8String.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)").map(_.trim)
        val sales = fields(12).toDouble
        val quantity = fields(13).toDouble
        val discount = fields(14).toDouble
        val profit = fields(15).toDouble

//        println((acc._1 + sales, acc._2 + quantity, acc._3 + discount, acc._4 + profit))
        (acc._1 + sales, acc._2 + quantity, acc._3 + discount, acc._4 + profit)
      }
      .map {
        result =>
        ByteString(s"${FinancialYear},${result._1},${result._2},${result._3},${result._4}\n")
      }
  }

  val categoryWiseFinancialYearSinkcsv = FileIO.toPath(Paths.get(categoryWiseFinancialYearSinkFile))

  fileSource
    .via(categoryFilterFlow)
    .via(financialYearAggregatorFlow)
    .to(categoryWiseFinancialYearSinkcsv).run()


}
