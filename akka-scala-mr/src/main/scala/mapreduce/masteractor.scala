package mapreduce

import com.typesafe.config.{ConfigFactory}
import akka.actor.{Actor, ActorRef,Props}
import akka.routing.{RoundRobinPool}


class MasterActor extends Actor{

    val numberMappers  = ConfigFactory.load.getInt("number-mappers")
    val numberReducers  = ConfigFactory.load.getInt("number-reducers")

    var reducers = List[ActorRef]()
    for(i <- 1 until numberReducers){
        context.actorOf(Props[ReduceActor](), name="reduce."+i)::reducers
    }

    val mappers = context.actorOf(RoundRobinPool(numberMappers).props(Props(classOf[MapActor], reducers)))
    
    def receive: Actor.Receive={
        case INITMAP(title, url)=>
            println("Initiate Master")
            mappers ! INITMAP(title, url)
    }

}
