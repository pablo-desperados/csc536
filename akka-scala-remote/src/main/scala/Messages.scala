package mapreduce
import akka.actor.{ActorRef}
import scala.collection.mutable.{HashMap}

case object SETUP
case class INITMAP(title: String, url: String)
case class INITMAPPER(item: String, url: String)
case class INIT_REDUCER(name: String, title: String) 
case class REGISTER_REDUCER(mapperRef: ActorRef)
case class REGISTER_MAPPER(reducerRef: ActorRef)
case class REDUCERS_SPAWNED(title: String)
case object FLUSH
case object DONE
case object MapperDone

