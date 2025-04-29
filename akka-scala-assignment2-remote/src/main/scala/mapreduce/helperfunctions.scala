package mapreduce
import scala.io.Source
import scala.compiletime.ops.string
import scala.collection.mutable.{HashMap, Seq}


def read_file(string_url: String): String = {

    try
        val content = Source.fromURL(string_url)
        return content.mkString
    catch 
        case e: Exception =>
            println(s"Not able to read text file from ${string_url}\nReason:\n")
            e.printStackTrace()
            return ""

}


def extract_names(title:String ,content: String, stop_words: List[String]): HashMap[String, String]={
    val dict = HashMap[String, String]()

    val split_content = content.split("\\s")


    for(word <- split_content){
        val trimmed = word.trim
        if(trimmed.matches("\\b[A-Z][a-z]*\\b") && (trimmed.length()>1 || trimmed.equals("I")) && !stop_words.contains(trimmed.toLowerCase)){
            dict put (trimmed, title)
        }
    }
    return dict
}


def reducer_hash_code(word: String, reducer_mod: Int)={
    Math.abs(word.hashCode() % reducer_mod)
}