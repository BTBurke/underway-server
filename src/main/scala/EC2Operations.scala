package underway.providers.ec2

import scala.collection.JavaConversions._
//import scalaz._, Scalaz._
import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.AmazonServiceException
import scala.language.implicitConversions
import underway.providers.ec2.models._
//import underway.providers.ec2.requests.DescribeInstancesResult
import underway.providers.ec2.conversions._
import scala.util.{Try, Success, Failure}

case class UnderwayServerException(msg: String) extends Exception {
	override def toString = "Error: " + msg
}

abstract class AWSClient {
	type Request[B] = Either[AmazonServiceException, B]
	def c: AmazonEC2Client
	def requestU[B](f: ()=>B): Request[B] = {
		try {
			Right(f.apply)
		} catch {
			case ex: AmazonServiceException => Left(ex)
		}

	}
	def requestA[A,B](f: A=>B)(args: A): B = {
		f.apply(args)
	}
}

trait EC2StateQuery extends AWSClient {

	def getReservations: Try[List[Reservation]] = Try(c.describeInstances()) map { _.getReservations().toList }

	def getInstances: Try[List[Instance]] = getReservations map { _ flatMap {x => x.instances} }

	def getInstancesFiltered(f: Instance => Boolean): Try[List[Instance]] = getInstances map { _ filter f}

	def getKeyPairs: Try[List[KeyPair]] = Try(c.describeKeyPairs()) map { _.getKeyPairs().toList }
	
	def describeAccountAttributes: String = c.describeAccountAttributes().toString()

	def getAddresses: Try[List[Address]] = Try(c.describeAddresses()) map { _.getAddresses().toList }

	def getAvailabilityZones: Try[List[AvailabilityZone]] = Try(c.describeAvailabilityZones()) map { _.getAvailabilityZones().toList }
	

}

trait EC2StateModifiers extends AWSClient {
	def testString2: String = return "testString2"
}


case class AWSConnection(accessKeyID: String, secretKey: String) extends EC2StateQuery with EC2StateModifiers {
	
	val c: AmazonEC2Client = clientConnection match {
		case Right(conn) => conn
		case Left(ex) => throw new UnderwayServerException(ex) 
	}

	private def testAuthorization(conn: AmazonEC2Client): Either[String, String] = {
		try {
			conn.describeAccountAttributes
			Right("Authorization success")
		} catch {
			case ex: AmazonServiceException => Left("Authorization failure")
		}
	}

	private def clientConnection: Either[String, AmazonEC2Client] = {
		val conn = new AmazonEC2Client(new BasicAWSCredentials(accessKeyID, secretKey))
		testAuthorization(conn) match {
			case Right(_) => Right(conn)
			case Left(ex) => Left(ex)
		}
	}
}

