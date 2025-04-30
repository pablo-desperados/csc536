package mapreduce
import com.typesafe.config.ConfigFactory
import akka.actor.{ActorSystem, Actor, Props}


object ClientMain extends App {
    val config = ConfigFactory.load("client.conf")
    val system = ActorSystem("MapReduceAppClient")
    val sources: String= ConfigFactory.load().getString("number-sources")
    val master = system.actorOf(Props[MasterActor](), name = "master")

    println("Client Ready")

    master ! SETUP

     Thread.sleep(3000)

    for(i <- 1 to sources.toInt){
        var source = ConfigFactory.load().getString("source-"+i)
        var split_source = source.split("\\|",2)
        var title = split_source(0)
        var url = split_source(1)
        master ! INITMAP(title: String, url: String)
    }

    
}