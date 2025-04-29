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