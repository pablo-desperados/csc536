package mapreduce

import com.typesafe.config.{ConfigFactory}
import akka.actor.{Actor, ActorRef,Props}
import akka.routing.{RoundRobinPool, Broadcast}
import scala.collection.mutable.{ArrayBuffer}


class MasterActor extends Actor{

    val numberMappers  = ConfigFactory.load.getInt("number-mappers")
    val numberReducers  = ConfigFactory.load.getInt("number-reducers")
    var pending = numberReducers

    var reducers = ArrayBuffer[ActorRef]()
    for(i <- 0 until numberReducers){
        reducers+=context.actorOf(Props[ReduceActor](), name="reduce."+i)
        print(reducers.size)
    }

    val mappers = context.actorOf(RoundRobinPool(numberMappers).props(Props(classOf[MapActor], reducers)))
    
    def receive: Actor.Receive={
        case INITMAP(title, url)=>
            println("Initiate Master")
            mappers ! INITMAP(title, url)
        case FLUSH =>
            mappers ! Broadcast(FLUSH)

        case DONE => 
            pending -= 1
            if (pending == 0)
                context.system.terminate()

    }

}
