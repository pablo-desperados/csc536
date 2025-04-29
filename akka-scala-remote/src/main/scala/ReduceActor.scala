package mapreduce

import akka.actor.{Actor, ActorRef}
import scala.collection.mutable.{HashMap, ListBuffer}
import com.typesafe.config.ConfigFactory
import java.io.{File, PrintWriter}


class ReduceActor extends Actor{
    val title_words = new HashMap[String, ListBuffer[String]]()

    def receive: Actor.Receive = {

        case INIT_REDUCER(name, title) =>
            val titles = title_words.getOrElseUpdate(name, ListBuffer())
            if (!titles.contains(title)) {
                    titles += title
                }
            
        case FLUSH =>
            println(s"<<<<<< Results from ${self.path.name} being written... >>>>>>")
            saveToFile()
            
    }


    def saveToFile() : Unit ={
        val outputFile = new File(s"${self.path.name}_results.txt")
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
