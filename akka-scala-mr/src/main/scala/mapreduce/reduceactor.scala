package mapreduce

import scala.collection.mutable.HashMap

import akka.actor.{Actor, ActorRef}
import com.typesafe.config.ConfigFactory

class ReduceActor extends Actor {

    def receive: Actor.Receive = {
        case _ =>
            println("hello")
    }
}