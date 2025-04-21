error id: scala/io/Source.
file://<WORKSPACE>/akka-scala-mr/src/main/scala/mapreduce/mapreduceapp.scala
empty definition using pc, found symbol in pc: scala/io/Source.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -scala/io/Source.
	 -scala/io/Source#
	 -scala/io/Source().
	 -scala/Predef.scala.io.Source.
	 -scala/Predef.scala.io.Source#
	 -scala/Predef.scala.io.Source().
offset: 109
uri: file://<WORKSPACE>/akka-scala-mr/src/main/scala/mapreduce/mapreduceapp.scala
text:
```scala
package mapreduce
import akka.actor.{ActorSystem}

object MapReduce extends App{
    val source = scala.io.So@@urce
    val system = ActorSystem("MapReduceApp")
    val master = system.actorOf(Props[MasterActor](), name = "master")

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/io/Source.