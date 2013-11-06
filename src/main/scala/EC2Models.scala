package underway.providers.ec2

import com.amazonaws.services.ec2.model.{InstanceLicense, 
	InstanceLicenseSpecification, InstanceNetworkInterfaceSpecification, Monitoring, Placement, InstanceState, StateReason}
import com.amazonaws.services.ec2.model.{Instance => JInstance, Reservation => JReservation, GroupIdentifier => JGroupIdentifier,
 	InstanceBlockDeviceMapping => JInstanceBlockDeviceMapping, InstanceNetworkInterface => JInstanceNetworkInterface, 
 	IamInstanceProfile => JIamInstanceProfile, ProductCode => JProductCode, Tag => JTag}
import underway.implicits.OptionalParams._
import scala.language.implicitConversions
import scala.collection.JavaConversions._


object models {

import underway.providers.ec2.conversions._

class Instance(val AmiLaunchIndex: Integer,
					val Architecture: String,
					val BlockDeviceMappings: List[BlockDeviceMapping],
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
					val NetworkInterfaces: List[NetworkInterface],
					val Placement: Placement,
					val Platform: String,
					val PrivateDnsName: String,
					val PrivateIpAddress: String,
					val ProductCodes: List[ProductCode],
					val PublicDnsName: String,
					val PublicIpAddress: String,
					val RamdiskId: String,
					val RootDeviceName: String,
					val RootDeviceType: String,
					val SecurityGroups: List[GroupIdentifier],
					val SpotInstanceRequestId: String,
					val State: InstanceState,
					val StateReason: StateReason,
					val StateTransitionReason: String,
					val SubnetId: String,
					val Tags: List[Tag],
					val VirtualizationType: String,
					val VpcId: String)

implicit class Reservation(jR: JReservation) {
	val GroupNames: List[String] = jR.getGroupNames().toList
	val Groups: List[GroupIdentifier] = jR.getGroups().toList
	val Instances: List[JInstance] = jR.getInstances().toList

	override def toString: String = s"GroupNames: ${GroupNames mkString ", "}\nGroups:     ${Groups mkString ", "}\nInstances:  ${Instances mkString ", "}"
}

implicit class NetworkInterface(jNI: JInstanceNetworkInterface) {
	???
}
implicit class BlockDeviceMapping(jBDM: JInstanceBlockDeviceMapping) {
	???
}

implicit class IamInstanceProfile(jIIP: JIamInstanceProfile) {
	val Arn: String = jIIP.getArn()
	val Id: String = jIIP.getId()

	def equals(that: IamInstanceProfile): Boolean = if (Tuple2(this.Arn, this.Id) == Tuple2(that.Arn, that.Id)) true else false
	override def toString = s"{Arn: $Arn, Id: $Id}"
}

implicit class ProductCode(jPC: JProductCode) {
	val ProductCodeId: String = jPC.getProductCodeId()
	val ProductCodeType: String = jPC.getProductCodeType()

	def equals(that: ProductCode): Boolean = if (Tuple2(this.ProductCodeId, this.ProductCodeType) == Tuple2(that.ProductCodeId, that.ProductCodeType)) true else false
	override def toString = s"{ProductCodeId: $ProductCodeId, ProductCodeType: $ProductCodeType}"
}

implicit class Tag(jT: JTag) {
	val Key: String = jT.getKey()
	val Value: String = jT.getValue()

	def equals(that: Tag): Boolean = if (Tuple2(this.Key, this.Value) == Tuple2(that.Key,that.Value)) true else false
	override def toString = s"{key: $Key, value: $Value}"	
}

implicit class GroupIdentifier(jGI: JGroupIdentifier) {
	val GroupId: String = jGI.getGroupId()
	val GroupName: String = jGI.getGroupName()

	override def toString: String = s"Test conversion\nGroupId: $GroupId\nGroupName: $GroupName"
	def equals(that: GroupIdentifier): Boolean = if (this.GroupId == that.GroupId && this.GroupName == that.GroupName) true else false
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

case class ExtendedRunRequest(val BlockDeviceMappings: Option[List[BlockDeviceMapping]] = None,
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

	implicit def List2ListRes(that: List[JReservation]): List[Reservation] = that map Reservation
	implicit def List2ListGI(that: List[JGroupIdentifier]): List[GroupIdentifier] = that map GroupIdentifier
	implicit def List2ListNetIface(that: List[JInstanceNetworkInterface]): List[NetworkInterface] = that map NetworkInterface
	implicit def List2ListBDM(that: List[JInstanceBlockDeviceMapping]): List[BlockDeviceMapping] = that map BlockDeviceMapping
	implicit def List2ListPC(that: List[JProductCode]): List[ProductCode] = that map ProductCode
	implicit def List2ListTag(that: List[JTag]): List[Tag] = that map Tag


}