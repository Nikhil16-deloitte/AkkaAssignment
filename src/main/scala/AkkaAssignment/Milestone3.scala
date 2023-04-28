import Milestone1.Record
import akka.actor.Actor
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.config.ConfigFactory

import scala.util.{Failure, Success, Try}



object Milestone3 {

  def personToCsv(person: Record): String = {
    s"${person.Category},${person.Name}\n"
  }

  val config=ConfigFactory.load("application.conf");
  val categorysinkfile = config.getString("StoreData.CategorySinkFile")
  class ChildActor extends Actor {
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    override def receive: Receive = {
      case record: Array[String] =>

        val result = Try(Record(record(0), record(1), record(2), record(3), record(4), record(5), record(6), record(7), record(8), record(9), record(10), record(11), record(12), record(13), record(14), record(15)))
        result match {
          case Success(data) =>

            if(validation(record)) {
              val source=Source.single(data)

              val sink=Sink.foreach(println)

              source.to(sink).run()



            }
          case Failure(ex) =>
            context.actorSelection("/user/masterActor/errorHandler") ! ex
        }
    }

  }
  def validation(record:Array[String]):Boolean= {
    for(i<-record)
    {
      if(i==""||i==null)
      {
        return false
      }
    }

    return true
  }

}
