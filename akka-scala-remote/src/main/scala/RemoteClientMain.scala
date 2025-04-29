package mapreduce

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import scala.concurrent.Await
import akka.util.Timeout

object  RemoteClientMain extends App{

    // val config = ConfigFactory.load("remote.conf")
    // val system = ActorSystem("RemoteMapReduce", config)

    // val masterPath = "akka://MapReduceAppClient@127.0.0.1:2552/user/master"
    // val master = system.actorSelection(masterPath)
    // println(s"Connectiong to Master at $masterPath...")
    // val mapper1 = system.actorOf(Props(classOf[MapActor], "RemoteMap1", null), "RemoteMap1")
    // val mapper2 = system.actorOf(Props(classOf[MapActor], "RemoteMap2", null), "RemoteMap2")

    // val reducer1 = system.actorOf(Props[ReduceActor](), "RemoteReduce1")
    // val reducer2 = system.actorOf(Props[ReduceActor](), "RemoteReduce2")

    val timeout: Timeout = Timeout(5.seconds)

}
