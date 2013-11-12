package underway.providers.ec2

import com.amazonaws.services.ec2.model.{InstanceLicenseSpecification, InstanceNetworkInterfaceSpecification}
import com.amazonaws.services.ec2.model.{
	Instance => JInstance, 
	Reservation => JReservation,
	GroupIdentifier => JGroupIdentifier,
 	InstanceBlockDeviceMapping => JInstanceBlockDeviceMapping,
 	InstanceNetworkInterface => JInstanceNetworkInterface, 
 	IamInstanceProfile => JIamInstanceProfile,
 	ProductCode => JProductCode,
 	Tag => JTag, 
 	InstanceNetworkInterfaceAssociation => JInstanceNetworkInterfaceAssociation, 
 	InstanceNetworkInterfaceAttachment => JInstanceNetworkInterfaceAttachment,
 	EbsInstanceBlockDevice => JEbsInstanceBlockDevice,
 	Placement => JPlacement,
 	Monitoring => JMonitoring,
 	StateReason => JStateReason,
 	InstanceState => JInstanceState,
 	InstanceLicense => JInstanceLicense,
 	KeyPairInfo => JKeyPairInfo,
 	Address => JAddress,
 	AvailabilityZone => JAvailabilityZone}
import underway.implicits.OptionalParams._
import scala.language.implicitConversions
import scala.collection.JavaConversions._


object models {

import underway.providers.ec2.conversions._

implicit class Instance(jI: JInstance) {
	val amiLaunchIndex: Integer = jI.getAmiLaunchIndex()
	val architecture: String = jI.getArchitecture()
	val blockDeviceMappings: List[BlockDeviceMapping] = jI.getBlockDeviceMappings().toList
	val clientToken: String = jI.getClientToken()
	val ebsOptimized: Boolean = jI.getEbsOptimized()
	val hypervisor: String = jI.getHypervisor()
	val iamInstanceProfile: JIamInstanceProfile = jI.getIamInstanceProfile()
	val imageId: String = jI.getImageId()
	val instanceId: String = jI.getInstanceId()
	val instanceLifecycle: String = jI.getInstanceLifecycle()
	val instanceType: String = jI.getInstanceType()
	val kernelId: String = jI.getKernelId()
	val keyName: String = jI.getKeyName()
	val launchTime: java.util.Date = jI.getLaunchTime()
	val license: JInstanceLicense = jI.getLicense()
	val monitoring: Monitoring = jI.getMonitoring()
	val networkInterfaces: List[NetworkInterface] = jI.getNetworkInterfaces().toList
	val placement: Placement = jI.getPlacement()
	val platform: String = jI.getPlatform()
	val privateDnsName: String = jI.getPrivateDnsName()
	val privateIpAddress: String = jI.getPrivateIpAddress()
	val productCodes: List[ProductCode] = jI.getProductCodes().toList
	val publicDnsName: String = jI.getPublicDnsName()
	val publicIpAddress: String = jI.getPublicIpAddress()
	val ramdiskId: String = jI.getRamdiskId()
	val rootDeviceName: String = jI.getRootDeviceName()
	val rootDeviceType: String = jI.getRootDeviceType()
	val securityGroups: List[GroupIdentifier] = jI.getSecurityGroups().toList
	val spotInstanceRequestId: String = jI.getSpotInstanceRequestId()
	val state: InstanceState = jI.getState()
	val stateReason: StateReason = jI.getStateReason()
	val stateTransitionReason: String = jI.getStateTransitionReason()
	val subnetId: String = jI.getSubnetId()
	val tags: List[Tag] = jI.getTags().toList
	val virtualizationType: String = jI.getVirtualizationType()
	val vpcId: String = jI.getVpcId()


	override def toString: String = jI.toString

}

implicit class Reservation(jR: JReservation) {
	val groupNames: List[String] = jR.getGroupNames().toList
	val groups: List[GroupIdentifier] = jR.getGroups().toList
	val instances: List[Instance] = jR.getInstances().toList

	override def toString: String = s"GroupNames: ${groupNames mkString ", "}\nGroups:     ${groups mkString ", "}\nInstances:  ${instances mkString ", "}"
	def equals(that: Reservation): Boolean = if (Tuple3(this.groupNames, this.groups, this.instances) == Tuple3(that.groupNames, that.groups, that.instances)) true else false
}

implicit class Placement(jP: JPlacement) {
	val availabilityZone: String = jP.getAvailabilityZone()
	val groupName: String = jP.getGroupName()
	val tenancy: String = jP.getTenancy()

	override def toString: String = s"{AvailabilityZone: $availabilityZone, GroupName: $groupName, Tenancy: $tenancy}"
	def equals(that: Placement): Boolean = if (Tuple3(this.availabilityZone,this.groupName,this.tenancy)==Tuple3(that.availabilityZone,that.groupName,that.tenancy)) true else false
}

implicit class Monitoring(jM: JMonitoring) {
	val state: String = jM.getState()

	override def toString: String = s"Monitoring: {State: $state}"
	def equals(that: Monitoring): Boolean = if (this.state == that.state) true else false
}

implicit class StateReason(jSR: JStateReason) {
	val code: String = jSR.getCode()
	val message: String = jSR.getMessage()

	override def toString: String = s"StateReason: {Code: $code, Message: $message}"
	def equals(that: StateReason) = if (Tuple2(this.code,this.message)==Tuple2(that.code, that.message)) true else false
}

implicit class InstanceState(jIS: JInstanceState) {
	val code: Integer = jIS.getCode()
	val name: String = jIS.getName()

	override def toString = s"InstanceState: {Code: $code, Name: $name}"
	def equals(that: InstanceState) = if (Tuple2(this.code, this.name)==Tuple2(that.code,that.name)) true else false
}

// This thing throws a NullPointer but option won't fix it, leaving it out for now
/*implicit class InstanceLicense(jIL: JInstanceLicense) {
	val pool: String = Option(jIL.getPool()).getOrElse("")

	override def toString = s"InstanceLicense: {Pool: $pool}"
	def equals(that: InstanceLicense) = if (this.pool==that.pool) true else false
}*/

implicit class NetworkInterface(jNI: JInstanceNetworkInterface) {
	val association: NetworkInterfaceAssociation = jNI.getAssociation()
	val attachment: NetworkInterfaceAttachment = jNI.getAttachment()
	val description: String = jNI.getDescription()
	val groups: List[GroupIdentifier] = jNI.getGroups().toList
	val networkInterfaceId: String = jNI.getNetworkInterfaceId()
	val ownerId: String = jNI.getOwnerId()
	val privateDnsName: String = jNI.getPrivateDnsName()
	val privateIpAddress: String = jNI.getPrivateIpAddress()
	val sourceDestCheck: Boolean = jNI.getSourceDestCheck()
	val status: String = jNI.getStatus()
	val subnetId: String = jNI.getSubnetId()
	val vpcId: String = jNI.getVpcId()

	private val fieldsAsTuple = Tuple12(association,attachment,description,groups,networkInterfaceId,
		ownerId,privateDnsName,privateIpAddress,sourceDestCheck,status,subnetId,vpcId)
	def equals(that: NetworkInterface): Boolean = if (this.fieldsAsTuple == that.fieldsAsTuple) true else false
	override def toString: String = jNI.toString
}

implicit class NetworkInterfaceAssociation(jNIA: JInstanceNetworkInterfaceAssociation) {
	val ipOwnerId: String = jNIA.getIpOwnerId()
	val publicDnsName: String = jNIA.getPublicDnsName()
	val publicIp: String = jNIA.getPublicIp()

	def equals(that: NetworkInterfaceAssociation): Boolean = if (Tuple3(this.ipOwnerId,this.publicDnsName,this.publicIp) == Tuple3(that.ipOwnerId,that.publicDnsName,that.publicIp)) true else false
	override def toString: String = s"{IpOwnerId: $ipOwnerId, PublicDnsName: $publicDnsName, PublicIp: $publicIp}"
}

implicit class NetworkInterfaceAttachment(jNIAt: JInstanceNetworkInterfaceAttachment) {
	val attachmentId: String = jNIAt.getAttachmentId()
	val attachTime: java.util.Date = jNIAt.getAttachTime()
	val deleteOnTermination: Boolean = jNIAt.getDeleteOnTermination()
	val deviceIndex: Integer = jNIAt.getDeviceIndex()
	val status: String = jNIAt.getStatus()

	def equals(that: NetworkInterfaceAttachment): Boolean = if (Tuple5(this.attachmentId,this.attachTime,this.deleteOnTermination,this.deviceIndex,this.status)==Tuple5(that.attachmentId,that.attachTime,that.deleteOnTermination,that.deviceIndex,that.status)) true else false	
	override def toString: String = jNIAt.toString
}

implicit class BlockDeviceMapping(jBDM: JInstanceBlockDeviceMapping) {
	val deviceName: String = jBDM.getDeviceName()
	val ebs: EbsInstanceBlockDevice = jBDM.getEbs()

	def equals(that: BlockDeviceMapping): Boolean = if (Tuple2(this.deviceName,this.ebs)==Tuple2(that.deviceName,that.ebs)) true else false
	override def toString: String = jBDM.toString

}

implicit class EbsInstanceBlockDevice(jEIBD: JEbsInstanceBlockDevice) {
	val attachTime: java.util.Date = jEIBD.getAttachTime()
	val deleteOnTermination: Boolean = jEIBD.getDeleteOnTermination()
	val status: String = jEIBD.getStatus()
	val volumeId: String = jEIBD.getVolumeId()

	private val fieldsAsTuple = Tuple4(attachTime,deleteOnTermination,status,volumeId)
	def equals(that: EbsInstanceBlockDevice): Boolean = if (this.fieldsAsTuple == that.fieldsAsTuple) true else false
	override def toString: String = jEIBD.toString
}
// There's something wrong here that causes NullPointerException when implicit cast to Scala class
/*implicit class IamInstanceProfile(jIIP: JIamInstanceProfile) {
	val arn: Option[String] = jIIP.getArn()
	val id: Option[String] = jIIP.getId()

	def equals(that: IamInstanceProfile): Boolean = if (Tuple2(this.arn, this.id) == Tuple2(that.arn, that.id)) true else false
	override def toString = s"{Arn: $arn, Id: $id}"
}
*/

implicit class ProductCode(jPC: JProductCode) {
	val productCodeId: String = jPC.getProductCodeId()
	val productCodeType: String = jPC.getProductCodeType()

	def equals(that: ProductCode): Boolean = if (Tuple2(this.productCodeId, this.productCodeType) == Tuple2(that.productCodeId, that.productCodeType)) true else false
	override def toString = s"{ProductCodeId: $productCodeId, ProductCodeType: $productCodeType}"
}

implicit class Tag(jT: JTag) {
	val key: String = jT.getKey()
	val value: String = jT.getValue()

	def equals(that: Tag): Boolean = if (Tuple2(this.key, this.value) == Tuple2(that.key,that.value)) true else false
	override def toString = s"{key: $key, value: $value}"	
}

implicit class GroupIdentifier(jGI: JGroupIdentifier) {
	val groupId: String = jGI.getGroupId()
	val groupName: String = jGI.getGroupName()

	override def toString: String = s"{GroupId: $groupId, GroupName: $groupName}"
	def equals(that: GroupIdentifier): Boolean = if (this.groupId == that.groupId && this.groupName == that.groupName) true else false
}

implicit class KeyPair(jKP: JKeyPairInfo) {
	val keyFingerprint: String = jKP.getKeyFingerprint()
	val keyName: String = jKP.getKeyName()

	override def toString: String = s"{KeyName: $keyName, KeyFingerprint: $keyFingerprint}"
}

implicit class Address(jA: JAddress) {
	val allocationId: String = jA.getAllocationId()
	val associationId: String = jA.getAssociationId()
	val domain: String = jA.getDomain()
	val instanceId: String = jA.getInstanceId()
	val networkInterfaceId: String = jA.getNetworkInterfaceId()
	val networkInterfaceOwnerId: String = jA.getNetworkInterfaceOwnerId()
	val privateIpAddress: String = jA.getPrivateIpAddress()
	val publicIp: String = jA.getPublicIp()

	override def toString = jA.toString
}

implicit class AvailabilityZone(jAZ: JAvailabilityZone) {
	val regionName: String = jAZ.getRegionName()
	val state: String = jAZ.getState()
	val zoneName: String = jAZ.getZoneName()

	override def toString = jAZ.toString
}

case class BaseRunRequest(val imageId: String,
                          val instanceType: String,
                          val keyName: String,
                          val minCount: Integer = 1,
                          val maxCount: Integer = 1,
                          val monitoring: Boolean = false,
                          val placement: Placement,
                          val securityGroups: List[String],
                          val securityGroupIds: List[String],
                          val userData: String = "",
                          val ebsOptimized: Boolean = false,
                          val instanceInitiatedShutdownBehavior: String = "terminate")

case class ExtendedRunRequest(val blockDeviceMappings: Option[List[BlockDeviceMapping]] = None,
	                          val clientToken: Option[String] = None,
	                          val iamInstanceProfile: Option[JIamInstanceProfile] = None,
	                          val kernelId: Option[String] = None,
	                          val license: Option[InstanceLicenseSpecification] = None,
	                          val networkInterfaces: Option[List[InstanceNetworkInterfaceSpecification]] = None,
	                          val privateIpAddress: Option[String] = None
	                          )

case class RunRequest(val base: BaseRunRequest, val ext: ExtendedRunRequest)

case class MachineDefFromFile(val imageId: String,
	                          val instanceType: String,
	                          val keyName: String,
	                          val minCount: Integer = 1,
	                          val maxCount: Integer = 1,
	                          val monitoring: Boolean = false,
	                          val placement: String,
	                          val securityDefs: List[String],
	                          val userData: String = "")
}

object conversions {
	import underway.providers.ec2.models._

	implicit def List2ListRes(that: List[JReservation]): List[Reservation] = that map Reservation
	implicit def List2ListGI(that: List[JGroupIdentifier]): List[GroupIdentifier] = that map GroupIdentifier
	implicit def List2ListNetIface(that: List[JInstanceNetworkInterface]): List[NetworkInterface] = that map NetworkInterface
	implicit def List2ListBDM(that: List[JInstanceBlockDeviceMapping]): List[BlockDeviceMapping] = that map BlockDeviceMapping
	implicit def List2ListPC(that: List[JProductCode]): List[ProductCode] = that map ProductCode
	implicit def List2ListTag(that: List[JTag]): List[Tag] = that map Tag
	implicit def List2ListInst(that: List[JInstance]): List[Instance] = that map Instance
	implicit def List2ListKeyPair(that: List[JKeyPairInfo]): List[KeyPair] = that map KeyPair
	implicit def List2ListAdd(that: List[JAddress]): List[Address] = that map Address
	implicit def List2ListAZ(that: List[JAvailabilityZone]): List[AvailabilityZone] = that map AvailabilityZone


}