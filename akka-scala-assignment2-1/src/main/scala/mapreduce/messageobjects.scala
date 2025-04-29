package mapreduce

import scala.collection.mutable.{HashMap}

case class INITMAP(title: String, url: String)

case class INIT_REDUCER(title_map:Map[String, String])
case object FLUSH
case object DONE