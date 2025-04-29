error id: scala/App#
file://<WORKSPACE>/akka-scala-assignment2-1/src/main/scala/mapreduce/mapreduceapp.scala
empty definition using pc, found symbol in pc: scala/App#
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -App#
	 -scala/Predef.App#
offset: 228
uri: file://<WORKSPACE>/akka-scala-assignment2-1/src/main/scala/mapreduce/mapreduceapp.scala
text:
```scala
package mapreduce

import com.typesafe.config.{ConfigFactory}
import akka.actor.{Actor, ActorRef,Props}
import akka.routing.{RoundRobinPool, Broadcast}
import scala.collection.mutable.{ArrayBuffer}


object MapReduce extends App@@{

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

```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/App#