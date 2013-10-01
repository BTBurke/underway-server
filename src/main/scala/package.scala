package underway.core.topology

import scalaz._, Scalaz._
import argonaut._, Argonaut._


case class TopologyDefinitionException(error: String) extends Exception

case class TopoFile(name: Option[String], provider: Option[String], machines: Option[List[String]], testField: Option[String])

object TopoFile {

	implicit def TopoFileCodecJson: CodecJson[TopoFile] = {
		casecodec4(TopoFile.apply, TopoFile.unapply)("name", "provider", "machines", "testField")
	}

	def parseWithValidation(json: String): Either[String, TopoFile] = {

		val topofile: Validation[String, TopoFile] = Parse.decodeValidation[TopoFile](json)
		
		val s: Either[String, TopoFile] = topofile match {
			case Success(s) => Right(s)
			case Failure(f) => Left("Failure: " + f)
		}
		return s
	}
}

//case class Topology(name: String, provider: String, machines: List[Machine], containers: List[Containers])