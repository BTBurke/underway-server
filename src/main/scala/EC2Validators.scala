package underway.core.validators

import scalaz._, Scalaz._
import underway.core.topology._

trait ValidationPipeline {
	def apply(c: String, f: List[String => Validation[String, Boolean]]): List[String]
	def filterFails(x: List[Validation[String,Boolean]]) = x filter { _ match {
		case Success(_) => false
		case Failure(_) => true 
	}}


}

object ValidationFunctionalTools {
	val keepFailsF = {x: Validation[String,Boolean] => x match {
		case Success(_) => false
		case Failure(_) => true
	}}

	val keepSuccessesF = {x: Validation[String, Boolean] => x match {
		case Success(_) => true
		case Failure(_) => false
		}}

	val extractStringsM = {x: Validation[String, Boolean] => x match {
		case Failure(x) => x.toString
		case Success(x) => x.toString
	}} 

	val validationToBooleanM = {x: Validation[String, Boolean] => x match {
		case Failure(_) => false
		case Success(_) => true
	}}

}

object utils {
	import underway.core.validators.ValidationFunctionalTools._

	implicit class richValidationOps(x: List[Validation[String, Boolean]]) {
		val allSuccesses = {
			val valsToBools = x map validationToBooleanM
			valsToBools allTrue
		}

		val anyFailure = !allSuccesses

	}

	implicit class richBoolOps(x: List[Boolean]) {
		
		val allTrue = x reduceLeft {_ && _}

		val anyFalse = !allTrue
	}
}

trait TopoFileValidation extends ValidationPipeline {
	import underway.core.validators.ValidationFunctionalTools._
	import underway.core.validators.utils._
	
	def apply(c: String, f: List[String => Validation[String, Boolean]]): List[String] = {

		val valid: List[Validation[String, Boolean]] = for { f1 <- f } yield f1(c)
		val validSuccesses = valid.anyFailure
		println("valid failures: " + validSuccesses)

		val pipe: List[String] = valid filter keepSuccessesF map extractStringsM
		println("pipe: " + pipe)
		return pipe
	}
}

object TopoFileValidators extends TopoFileValidation {

	val allValidators = List(testValidatorTrue _, testValidatorError _)

	def testValidatorError(c: String) = if (true) "Test error msg".failure else true.success

	def testValidatorTrue(c: String) = if (true) true.success else "fuck".failure
	
	def validateAll(c: String): Boolean = {
		val r = apply(c, allValidators)
		println(r)
		return true

	}
}