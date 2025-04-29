package mapreduce

import akka.actor.{Actor, ActorRef}

class MapActor(name: String, reducerRouter: ActorRef) extends Actor{
    val nameActor = name
    val router = reducerRouter
    val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at", "be",
    "do", "go", "if", "in", "is", "it", "of", "on", "the", "to")

    def receive: Actor.Receive = {
        case INITMAPPER(title,url) =>
             val content = read_file(url)
             val list_of_names = extract_names(title,content, STOP_WORDS_LIST)
             list_of_names.foreach { case (name, title) =>
                router ! INIT_REDUCER(name, title)
            }
            context.parent ! MapperDone
        
        case DONE =>
            context.parent ! DONE

            
        
    }
}
