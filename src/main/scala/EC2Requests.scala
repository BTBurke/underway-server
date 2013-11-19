package underway.providers.ec2

import scala.language.implicitConversions
import scala.collection.JavaConversions._
import com.amazonaws.services.ec2.model.{
	DescribeInstancesResult => JDescribeInstancesResult} 
import underway.providers.ec2.models._
import underway.providers.ec2.conversions._
import scalaz._, Scalaz._



object requests {

	implicit class DescribeInstancesResult(jDIR: JDescribeInstancesResult) {
		def getReservations = jDIR.getReservations()
		//final def map[B](f: DescribeInstancesResult => B): B = this map f
	}
}