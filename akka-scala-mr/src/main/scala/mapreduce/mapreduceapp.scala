package mapreduce
import com.typesafe.config.ConfigFactory
import akka.actor.{ActorSystem, Actor, Props}

object MapReduce extends App{

    val system = ActorSystem("MapReduceApp")
    val master = system.actorOf(Props[MasterActor](), name = "master")

    for(i <- 1 to 1){
        var source = ConfigFactory.load().getString("source-"+i)
        var split_source = source.split("\\|",2)
        var title = split_source(0)
        var url = split_source(1)
        
    }

   
    



}
