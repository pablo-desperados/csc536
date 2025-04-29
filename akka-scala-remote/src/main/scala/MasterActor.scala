package mapreduce

import akka.actor.{Actor, ActorRef,Props}
import akka.remote.routing.{RemoteRouterConfig}
import scala.collection.mutable.{HashMap}
import akka.routing.{RoundRobinRoutingLogic, Pool, ConsistentHashingPool, ActorRefRoutee, ConsistentHashingGroup, Broadcast}
import com.typesafe.config.ConfigFactory
import mapreduce._
import akka.routing.Router
import akka.actor.Props.EmptyActor

class MasterActor extends Actor{

    var mapRouter: Router = Router(RoundRobinRoutingLogic(),Vector())
    var reduceRouter: ActorRef =  context.actorOf(Props[ReduceActor]())
    var listofMappers: Vector[ActorRef] = Vector()
    var listofReducers: Vector[ActorRef] = Vector()
    var mappersDone = 0
    var totalMappers= 0
    var reducersDone = 0
    var totalReducers = reducersDone


    def receive = {
        case SETUP=>
            println("*********** Starting MapReduce Process ***********")

            val localReduce1 = context.actorOf(Props(classOf[ReduceActor]), name = "RemoteReduce" + 1)
            val localReduce2 = context.actorOf(Props(classOf[ReduceActor]), name = "RemoteReduce" + 2)
            totalReducers+=2
            val listofReducers = Vector(
                localReduce1,
                localReduce2
            )

            reduceRouter = context.actorOf(
                ConsistentHashingGroup(
                    paths=listofReducers.map(e=> e.path.toStringWithoutAddress),
                    hashMapping= hashingFunc
                ).props(),
                name = "reducerRouter"
            )

            println("*********** Reducers Setup Finished ***********")

            
            val localMap1 = context.actorOf(Props(classOf[MapActor], "Map1", reduceRouter), name = "Map1")
            val localMap2 = context.actorOf(Props(classOf[MapActor], "Map2", reduceRouter), name = "Map2")
           


            val listofMappers = Vector(
                localMap1,
                localMap2
            )

            mapRouter = Router(RoundRobinRoutingLogic(), listofMappers.map(e=> ActorRefRoutee(e)).toVector)

            println("*********** Mapper Setup Finished ***********")
        
        case INITMAP(title, url) => 
            this.mapRouter.route(INITMAPPER(title, url), sender())
             totalMappers+=1

        case MapperDone =>
            mappersDone += 1
            if (mappersDone == totalMappers) {
                println("✅ All mappers are done! Tell reducers to FLUSH!")
                this.reduceRouter ! Broadcast(FLUSH)
            }
        case DONE => 
            reducersDone += 1
            if (reducersDone == totalReducers) {
                println("✅ All reducers have finished writing results! Job done!")
                context.system.terminate()
            }
                


            
    }
}

