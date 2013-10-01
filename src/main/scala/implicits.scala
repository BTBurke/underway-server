package underway.implicits

import scala.language.implicitConversions

object OptionalParams {
	implicit def type2Option[T](that: T): Option[T] = Some(that)
}