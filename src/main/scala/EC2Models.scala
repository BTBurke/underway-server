package underway.providers.ec2

import com.amazonaws.services.ec2.model.{InstanceBlockDeviceMapping, 
	IamInstanceProfile, InstanceNetworkInterface, ProductCode, InstanceLicense, 
	InstanceLicenseSpecification, InstanceNetworkInterfaceSpecification, Monitoring, Placement, GroupIdentifier}
import com.amazonaws.services.ec2.model.{Instance => JInstance, Reservation => JReservation}
import underway.implicits.OptionalParams._
import scala.language.implicitConversions
import scala.collection.JavaConversions._


object models {

case class Instance(val AmiLaunchIndex: Integer,
					val Architecture: String,
					val BlockDeviceMappings: List[InstanceBlockDeviceMapping],
					val ClientToken: String,
					val EbsOptimized: Boolean,
					val Hypervisor: String,
					val IamInstanceProfile: IamInstanceProfile,
					val ImageId: String,
					val InstanceId: String,
					val InstanceLifecycle: String,
					val InstanceType: String,
					val KernelId: String,
					val KeyName: String,
					val LaunchTime: java.util.Date,
					val License: InstanceLicense,
					val Monitoring: Monitoring,
					val NetworkInterfaces: List[InstanceNetworkInterface],
					val Placement: Placement,
					val Platform: String,
					val PrivateDnsName: String,
					val PrivateIpAddress: String,
					val ProductCodes: List[ProductCode])

implicit class Reservation(jR: JReservation) {
	val GroupNames: List[String] = jR.getGroupNames().toList
	val Groups: List[GroupIdentifier] = jR.getGroups().toList
	val Instances: List[JInstance] = jR.getInstances().toList

	override def toString: String = s"GroupNames: ${GroupNames mkString ", "}\nGroups:     ${Groups mkString ", "}\nInstances:  ${Instances mkString ", "}"
}




case class BaseRunRequest(val ImageId: String,
                          val InstanceType: String,
                          val KeyName: String,
                          val MinCount: Integer = 1,
                          val MaxCount: Integer = 1,
                          val Monitoring: Boolean = false,
                          val Placement: Placement,
                          val SecurityGroups: List[String],
                          val SecurityGroupIds: List[String],
                          val UserData: String = "",
                          val EbsOptimized: Boolean = false,
                          val InstanceInitiatedShutdownBehavior: String = "terminate")

case class ExtendedRunRequest(val BlockDeviceMappings: Option[List[InstanceBlockDeviceMapping]] = None,
	                          val ClientToken: Option[String] = None,
	                          val IamInstanceProfile: Option[IamInstanceProfile] = None,
	                          val KernelId: Option[String] = None,
	                          val License: Option[InstanceLicenseSpecification] = None,
	                          val NetworkInterfaces: Option[List[InstanceNetworkInterfaceSpecification]] = None,
	                          val PrivateIpAddress: Option[String] = None
	                          )

case class RunRequest(val base: BaseRunRequest, val ext: ExtendedRunRequest)

case class MachineDefFromFile(val ImageId: String,
	                          val InstanceType: String,
	                          val KeyName: String,
	                          val MinCount: Integer = 1,
	                          val MaxCount: Integer = 1,
	                          val Monitoring: Boolean = false,
	                          val Placement: String,
	                          val SecurityDefs: List[String],
	                          val UserData: String = "")
}

object conversions {
	import underway.providers.ec2.models._

	//private def listConverter[A,B](that: List[A])(f: A=>B): List[B] = that map f

	implicit def List2List(that: List[JReservation]): List[Reservation] = that map Reservation
	//implicit def List2List[A,B](that: List[A]): List[B] = listConverter(that)(B)
}