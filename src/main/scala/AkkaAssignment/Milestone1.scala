
import Milestone2.ReaderActor
import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory



object Milestone1 extends App {

  import scala.io.Source

  case class Record(OrderDate: String, ShipDate: String,	ShipMode: String,	CustomerName: String,	Segment : String,	Country : String,	City: String,	State: String,	Region: String ,	Category: String , 	SubCategory: String,	Name: String,	Sales: String,	Quantity: String,	Discount: String,	Profit: String)

  val config = ConfigFactory.load("application.conf")
  val filepath = config.getString("StoreData.filepath")

  val source = Source.fromFile(filepath)
  val lines = source.getLines().drop(1)

  val system = ActorSystem("FileReader")
  val fileReaderActor = system.actorOf(Props[ReaderActor], "fileReaderActor")

  for (line <- lines) {
    val record = line.split(",")
//    println(line.split(",").toList)
    fileReaderActor ! record
  }


}





