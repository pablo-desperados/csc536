package mapreduce

import akka.actor._
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object  RemoteClientMain extends App{

    val config = ConfigFactory.load("remote.conf")
    val system = ActorSystem("RemoteMapReduce", config)
    val masterPath = "akka://MapReduceAppClient@127.0.0.1:9090/user/master"


    def tryRegister(): Unit = {
        println(s"trying to locate master at $masterPath")
        system.actorSelection(masterPath).resolveOne(2.seconds).onComplete {
        case Success(masterRef) =>
            println("successfully connected to master.")
            for (i <- 1 to 2) {
            val mapper = system.actorOf(Props(classOf[MapActor], s"RemoteMap$i", masterRef), name =  s"RemoteReduce-${System.nanoTime}")
            masterRef ! REGISTER_MAPPER(mapper)
            }

            for (i <- 1 to 2) {
            val reducer = system.actorOf(Props(classOf[ReduceActor]), name =  s"RemoteMap-${System.nanoTime}")
            masterRef ! REGISTER_REDUCER(reducer)
            }

        case Failure(_) =>
            println("Could not connect to master. Retrying in 2 seconds...")
            system.scheduler.scheduleOnce(2.seconds)(tryRegister())
        }
    }
    tryRegister()
}
