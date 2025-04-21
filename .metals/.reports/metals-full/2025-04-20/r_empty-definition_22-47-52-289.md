error id: actor.
file://<WORKSPACE>/akka-scala-mr/src/main/scala/mapreduce/mapreduceapp.scala
empty definition using pc, found symbol in pc: actor.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -akka/actor.
	 -scala/Predef.akka.actor.
offset: 34
uri: file://<WORKSPACE>/akka-scala-mr/src/main/scala/mapreduce/mapreduceapp.scala
text:
```scala
package mapreduce
import akka.acto@@r.{ActorSystem, Actor, Props}

object MapReduce extends App{
    val text1 = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/data/text2-christmas-carol.txt"))
    println(text1.mkString)
    val system = ActorSystem("MapReduceApp")
    val master = system.actorOf(Props[Actor](), name = "master")

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: actor.