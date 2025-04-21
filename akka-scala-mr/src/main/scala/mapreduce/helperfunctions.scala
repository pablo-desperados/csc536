package mapreduce
import scala.io.Source
import scala.compiletime.ops.string


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


def extract_names(content: String)=
