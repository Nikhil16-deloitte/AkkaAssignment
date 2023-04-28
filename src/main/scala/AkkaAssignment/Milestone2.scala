import Milestone3.ChildActor
import Milestone4ErrorHandler.ErrorHandler
import akka.actor.{Actor, ActorRef, Props, Terminated}
import akka.routing.RoundRobinPool

object Milestone2 extends App {

  class ReaderActor extends Actor {

    val workerRouter: ActorRef = context.actorOf(RoundRobinPool(10).props(Props[ChildActor]), "workerRouter")
    val errorHandler: ActorRef = context.actorOf(Props[ErrorHandler], "errorHandler")

    override def receive: Receive = {
      case record: Array[Any] =>
        workerRouter ! record
      case Terminated(child) =>
        println(s"Child actor ${child.path.name} terminated")

    }
  }
}
