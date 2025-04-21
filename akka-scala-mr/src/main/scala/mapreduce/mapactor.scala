package mapreduce

import akka.actor.{Actor, ActorRef}

class MapActor(reducers: List[ActorRef]) extends Actor{
  

    val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at", "be",
    "do", "go", "if", "in", "is", "it", "of", "on", "the", "to")
    
    val numReducers = reducers.size

    def receive: Actor.Receive = {
        case INITMAP(title,url) =>
            val content = read_file(url)
            val list_of_names =

    }
}
