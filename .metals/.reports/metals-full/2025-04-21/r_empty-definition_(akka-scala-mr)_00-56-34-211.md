error id: mapreduce/`<import>`.
file://<WORKSPACE>/akka-scala-mr/src/main/scala/mapreduce/mapactor.scala
empty definition using pc, found symbol in pc: mapreduce/`<import>`.
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 59
uri: file://<WORKSPACE>/akka-scala-mr/src/main/scala/mapreduce/mapactor.scala
text:
```scala
package mapreduce

import akka.actor.{Actor, ActorRef}
impo@@rt mapreduce.INIT
class MapActor(reducers: List[ActorRef]) extends Actor{
  

    val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at", "be",
    "do", "go", "if", "in", "is", "it", "of", "on", "the", "to")
    
    val numReducers = reducers.size

    def receive: Actor.Receive = {
        case INITMAP(title,url) =>

    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: mapreduce/`<import>`.