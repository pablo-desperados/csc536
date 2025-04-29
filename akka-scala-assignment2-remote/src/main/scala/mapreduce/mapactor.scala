package mapreduce

import akka.actor.{Actor, ActorRef}
import scala.collection.mutable.{HashMap, ListBuffer, ArrayBuffer}

class MapActor(reducers: ArrayBuffer[ActorRef]) extends Actor{
  

    val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at", "be",
    "do", "go", "if", "in", "is", "it", "of", "on", "the", "to")
    
    val numReducers = reducers.size

    def receive: Actor.Receive = {
        case INITMAP(title,url) =>
            val content = read_file(url)
            val list_of_names = extract_names(title,content, STOP_WORDS_LIST)
            list_of_names.foreach { case (name, title) =>
                val reducerIndex = reducer_hash_code(name, numReducers)
                reducers(reducerIndex) ! INIT_REDUCER(Map(name -> title))
            }
            // val reducerBatches = new HashMap[Int, HashMap[String, String]]()
            // list_of_names.foreach((name, title)=>{
            //     var reducer_index = reducer_hash_code(name, numReducers)
            //     val reducer_map = reducerBatches.getOrElseUpdate(reducer_index, new HashMap[String, String]())
            //     if (!reducer_map.contains(title)) {
            //         reducer_map put (name, title)
            //     }
            // })

            // for((reduce, title_map) <- reducerBatches){
            //     reducers(reduce) ! INIT_REDUCER(title_map)
            // }
        
        case FLUSH=>
            for (i <- 0 until numReducers) {
                reducers(i) ! FLUSH
            }
        
    }
}
