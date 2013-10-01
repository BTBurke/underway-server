package underway.core

import scalaz._, Scalaz._
import argonaut._, Argonaut._
import underway.core.topology._
import underway.core.validators._
import underway.providers.ec2.AWSConnection



object CoreApp extends App {

	val test = """
	{	
		"name": "TestTop"	
		,"provider": "ec2"
		,"machines": ["machine1"]
	}
	"""
	try {
		//val testDecode = test.decodeOption[TopoFile]
		//val testDecode: (String \/ (String, CursorHistory)) \/ TopoFile = Parse.decode[TopoFile](test)
		//val topfile = testDecode match {
		//	case \/-(t) => t
		//	case -\/(msg) => throw new TopologyDefinitionException("Got a parsing error: " + msg)
		//	case \/-((msg, history)) => throw new TopologyDefinitionException("Got error: " + msg + " at " + history)
		//}
		val testDecode = TopoFile.parseWithValidation(test)
		val testValidate = TopoFileValidators.validateAll("test string")
		println("Completed...") 
		println(testDecode)
		//println(testDecode.machines)

		val conn = AWSConnection("AKIAJ27NCGA4HJV4YDTA", "dnUxDb7DKy+TXdEjByo6caj45Or2wNbZ7+U+6Tmx")
	
		val testAWS = conn.instanceDescriptions
		println("AWS: " + testAWS.mkString("\n"))

		} 
	catch {
		case ex: TopologyDefinitionException => println("Error parsing topology definition. " + ex.error)
		case ex: IllegalArgumentException => println("WTF")
	} 

}
