package mapreduce

import akka.actor.{Actor, ActorRef,Props}
import akka.remote.routing.{RemoteRouterConfig}
import scala.collection.mutable.{HashMap}
import akka.routing.{GetRoutees, RoundRobinRoutingLogic, ConsistentHash,Pool, ConsistentHashingRoutingLogic, ActorRefRoutee, ConsistentHashingGroup, Broadcast}
import com.typesafe.config.ConfigFactory
import mapreduce._
import akka.routing.Router
import akka.actor.Props.EmptyActor
import akka.actor.ActorSystem
import akka.routing.RoutingLogic

class MasterActor extends Actor{

    val sources: String= ConfigFactory.load().getString("number-sources")
    var mapRouter: Router = Router(RoundRobinRoutingLogic(),Vector())
    var reduceRouter: Router = Router(ConsistentHashingRoutingLogic(context.system,hashMapping = hashingFunc), Vector())
    var listofMappers: Vector[ActorRef] = Vector()
    var listofReducers: Vector[ActorRef] = Vector()
    var mappersDone = 0
    var totalMappers= 0
    var reducersDone = 0
    var totalFilesToProcess = sources.toInt
    var filesProcessed = 0
    var totalReducers = reducersDone

    def receive = {
        case REGISTER_MAPPER(mapperRef)=>
            println(s"Remote mapper registered: ${mapperRef.path}")
            mapRouter = mapRouter.addRoutee(mapperRef)
            totalMappers+=1
        
        case REGISTER_REDUCER(mapperRef) => 
            println(s"Remote reducer registered: ${mapperRef.path}")
            listofReducers :+= mapperRef
            totalReducers += 1
            this.reduceRouter = reduceRouter.addRoutee(ActorRefRoutee(mapperRef))


        case SETUP=>
            println("*********** Starting MapReduce Process ***********")

            val localReduce1 = context.actorOf(Props(classOf[ReduceActor]), name = s"LocalReduce-${System.nanoTime}")
            val localReduce2 = context.actorOf(Props(classOf[ReduceActor]), name = s"LocalReduce-${System.nanoTime}")
            totalReducers+=2
            this.listofReducers = Vector(
                localReduce1,
                localReduce2
            )

            reduceRouter=reduceRouter.addRoutee(ActorRefRoutee(localReduce1))
            reduceRouter=reduceRouter.addRoutee(ActorRefRoutee(localReduce2))


            println("*********** Reducers Setup Finished ***********")

            
            val localMap1 = context.actorOf(Props(classOf[MapActor], "Map1"), name =  s"LocalMap-${System.nanoTime}")
            val localMap2 = context.actorOf(Props(classOf[MapActor], "Map2"), name =  s"LocalMap-${System.nanoTime}")
            totalMappers+=1


            val listofMappers = Vector(
                localMap1,
                localMap2
            )

            mapRouter = Router(RoundRobinRoutingLogic(), listofMappers.map(e=> ActorRefRoutee(e)).toVector)

            println("*********** Mapper Setup Finished ***********")
        
        case INITMAP(title, url) => 
            this.mapRouter.route(INITMAPPER(title, url), sender())
        
        case INIT_REDUCER(name, title) => 
            this.reduceRouter.route( INIT_REDUCER(name, title), sender())

        case MapperDone =>
            filesProcessed += 1
            if (filesProcessed == totalFilesToProcess) {
                println("All files processed! Telling reducers to FLUSH.")
                listofReducers.foreach(_ ! FLUSH)
            }
        case DONE => 
            reducersDone += 1
            println(s"reducersDone<${reducersDone}>-totalReducer${totalReducers}")
            if (reducersDone == totalReducers) {
                println("All reducers have finished writing results! Job done!")
                context.system.terminate()
            }
                


            
    }
}

