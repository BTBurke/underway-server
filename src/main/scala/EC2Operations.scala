package underway.providers.ec2

import scala.collection.JavaConversions._
import scalaz._, Scalaz._
import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.AmazonServiceException

case class UnderwayServerException(msg: String) extends Exception {
	override def toString = "Error: " + msg
}

abstract class AWSClient {
	def c: AmazonEC2Client
}

trait EC2StateQuery extends AWSClient {
	//import underway.core.providers.EC2.AWSConnection._

	def instanceStatuses: List[Tuple2[String, Map[String, String]]] = {
		val instData = for (instance <- c.describeInstanceStatus().getInstanceStatuses()) yield (instance.getInstanceId(), 
														Map("state" -> instance.getInstanceState().getName()))
		return instData.toList
	}

	def instanceDescriptions: List[String] = {
		val res = c.describeInstances().getReservations()
		println("Reservations: " + res.length.toString)
		val ret = for (inst1 <- res) yield inst1.toString()
		ret.toList
	}

	def testString: String = return "Test String"

	def describeAccountAttributes: String = c.describeAccountAttributes().toString()

}

trait EC2StateModifiers extends AWSClient {
	def testString2: String = return "testString2"
}


case class AWSConnection(accessKeyID: String, secretKey: String) extends EC2StateQuery with EC2StateModifiers {
	
	val c: AmazonEC2Client = clientConnection match {
		case Right(conn) => conn
		case Left(ex) => throw new UnderwayServerException(ex) 
	}

	private def testAuthorization(conn: AmazonEC2Client): Validation[String, String] = {
		try {
			conn.describeAccountAttributes
			"Authorization success".success
		} catch {
			case ex: AmazonServiceException => "Authorization failure".failure
		}
	}

	private def clientConnection: Either[String, AmazonEC2Client] = {
		val conn = new AmazonEC2Client(new BasicAWSCredentials(accessKeyID, secretKey))
		testAuthorization(conn) match {
			case Success(_) => Right(conn)
			case Failure(ex) => Left(ex)
		}
	}
}
