error id: scala/io/BufferedSource#reader().
file://<WORKSPACE>/akka-scala-mr/src/main/scala/mapreduce/mapreduceapp.scala
empty definition using pc, found symbol in pc: scala/io/BufferedSource#reader().
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -text1/reader.
	 -text1/reader#
	 -text1/reader().
	 -scala/Predef.text1.reader.
	 -scala/Predef.text1.reader#
	 -scala/Predef.text1.reader().
offset: 235
uri: file://<WORKSPACE>/akka-scala-mr/src/main/scala/mapreduce/mapreduceapp.scala
text:
```scala
package mapreduce
import akka.actor.{ActorSystem, Actor, Props}

object MapReduce extends App{
    val text1 = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/data/text2-christmas-carol.txt"))
    val text = text1.reader@@()
    println(text)
    text1.close()
    val system = ActorSystem("MapReduceApp")
    val master = system.actorOf(Props[Actor](), name = "master")

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/io/BufferedSource#reader().