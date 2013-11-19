package underway.parsers

import org.yaml.snakeyaml.{Yaml, TypeDescription}
import org.yaml.snakeyaml.constructor.Constructor
import scala.collection.mutable.Map
import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scala.reflect.BeanProperty
import java.lang.RuntimeException

trait ParseResult[A]
case class ParseError(prop: String, line: Int) extends ParseResult[Any]
case class ParseOk[T](res: T) extends ParseResult[T]

object yaml {

	class ListItem {
		@BeanProperty var name: String = ""
		override def toString = s"{name: $name}"
	}

	class Hello {
		@BeanProperty var hello: String = ""
		@BeanProperty var mylist = new java.util.ArrayList[ListItem]()

		override def toString = s"{hello: $hello, mylist: $mylist}"
	}
	//object Hello {
//		def apply: Hello = new Hello("")
//		def apply(s: String) = new Hello(s)
//	}

	def testYaml(s: String): Unit = {
		val constructor: Constructor = new Constructor(classOf[Hello])
		val helloDescription: TypeDescription = new TypeDescription(classOf[Hello])
		constructor.addTypeDescription(helloDescription)

		val yaml = new Yaml(constructor)
		
		val errors = validate[Hello](yaml, s)
		println(errors)
	}

	def validate[T](yDecoder: Yaml, s: String, pError: List[ParseError] = List()): List[ParseError] = {
		val result = try {
			ParseOk[T](yDecoder.load(s).asInstanceOf[T])
		} catch {
			case s: Exception => extractError(s.toString)
		}
		result match {
			case x: ParseError => validate[T](yDecoder, dropLine(s, x.line-1), x :: pError)
			case y: ParseOk[T] => pError
		}
	}

	private def dropLine(s: String, l: Int): String = {
		val sAsVec = s.split("\n").toVector
		val dropped = sAsVec.zipWithIndex.filterNot({_._2 == l}).unzip._1
		return dropped.mkString("\n")
	}

	private def extractError(s: String): ParseError = {
		val sAsList = s.split("\n")

		def loop(s: Array[String]): ParseError = {
			if (s.head startsWith "Unable to find property") extract(s.head, s.tail.head) else loop(s.tail)
		}
		def extract(l1: String, l2: String) = {
			val r1 = """Unable to find property '([a-zA-Z0-9]*)'""".r
			val r2 = """ in 'string', line ([0-9]*)""".r

			val prop = r1 findFirstIn l1 match {
				case Some(r1(prop)) => prop
				case None => "Unknown parse error"
			}

			val line = r2 findFirstIn l2 match {
				case Some(r2(line)) => line.toInt
				case None => 0
			}
			ParseError(prop, line)
		}
		loop(sAsList)
	} 
}
