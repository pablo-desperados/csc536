error id: mapreduce/INITMAP.
file://<WORKSPACE>/akka-scala-mr/src/main/scala/mapreduce/mapactor.scala
empty definition using pc, found symbol in pc: mapreduce/INITMAP.
semanticdb not found

found definition using fallback; symbol INITMAP
offset: 354
uri: file://<WORKSPACE>/akka-scala-mr/src/main/scala/mapreduce/mapactor.scala
text:
```scala
package mapreduce

import akka.actor.{Actor, ActorRef}

class MapActor(reducers: List[ActorRef]) extends Actor{
  

    val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at", "be",
    "do", "go", "if", "in", "is", "it", "of", "on", "the", "to")
    
    val numReducers = reducers.size

    def receive: Actor.Receive = {
        case INI@@TMAP(title,url) =>
            

    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: mapreduce/INITMAP.