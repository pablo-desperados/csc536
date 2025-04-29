file://<WORKSPACE>/akka-scala-assignment2-1/src/main/scala/mapreduce/reduceactor.scala
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
uri: file://<WORKSPACE>/akka-scala-assignment2-1/src/main/scala/mapreduce/reduceactor.scala
text:
```scala
package mapreduce

import scala.collection.mutable.{HashMap, ListBuffer}

import akka.actor.{Actor, ActorRef}
import com.typesafe.config.ConfigFactory
import java.io.{File, PrintWriter}


class ReduceActor extends Actor {
    var remainingMappers = ConfigFactory.load.getInt("number-mappers")
    val name =self.path.name
    val title_words = new HashMap[String, ListBuffer[String]]()

    def receive: Actor.Receive = {
        case INIT_REDUCER(item_map) =>
            item_map.foreach{case(name, title)=>
                val titles = title_words.getOrElseUpdate(name, ListBuffer())
                if (!titles.contains(title)) {
                    titles += title
                }
            }
        
        case FLUSH =>
            remainingMappers -= 1
            if(remainingMappers == 0){
                println(s"<<<<<< Results from ${name} being written... >>>>>>")
                saveToFile()
            }
          
    }

    def saveToFile() : Unit ={
        val outputFile = new File(s"${name}_results.txt")
        val writer = new PrintWriter(outputFile)
        for ((name, titles) <- title_words.toSeq.sortBy(_._1)) {
            val formatted = s"<Name>: $name - <Title_Count>: ${titles.size} - <Titles>: [${titles.mkString(", ")}]"
            writer.println(formatted)
        }
        writer.close()
        println(s"Results saved to ${outputFile.getAbsolutePath}")
        context.parent ! DONE

    }
}
```



#### Error stacktrace:

```
dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2607)
	dotty.tools.dotc.core.SymDenotations$SymDenotation.isSelfSym(SymDenotations.scala:715)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:322)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.fold$1(Trees.scala:1636)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.apply(Trees.scala:1638)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1669)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1771)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1677)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1771)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.fold$1(Trees.scala:1636)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.apply(Trees.scala:1638)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1675)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1771)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse$$anonfun$13(ExtractSemanticDB.scala:383)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:378)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1669)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1771)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1677)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1771)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.fold$1(Trees.scala:1636)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.apply(Trees.scala:1638)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1675)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1771)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1724)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1771)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:346)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.fold$1(Trees.scala:1636)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.apply(Trees.scala:1638)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1669)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1771)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1724)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1771)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:346)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse$$anonfun$11(ExtractSemanticDB.scala:369)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:369)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1770)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1728)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1642)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1771)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:343)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse$$anonfun$1(ExtractSemanticDB.scala:307)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:307)
	dotty.tools.pc.SemanticdbTextDocumentProvider.textDocument(SemanticdbTextDocumentProvider.scala:36)
	dotty.tools.pc.ScalaPresentationCompiler.semanticdbTextDocument$$anonfun$1(ScalaPresentationCompiler.scala:242)
```
#### Short summary: 

java.lang.AssertionError: NoDenotation.owner