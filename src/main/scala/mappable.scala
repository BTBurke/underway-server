import scala.language.experimental.macros
import scala.reflect.macros.Context

trait Model

object Model {
  implicit class Mappable[M <: Model](val model: M) extends AnyVal {
    def asMap: Map[String, Any] = macro Macros.asMap_impl[M]
  }

  private object Macros {
    import scala.reflect.macros.Context

    def asMap_impl[T: c.WeakTypeTag](c: Context) = {
      import c.universe._

      val mapApply = Select(reify(Map).tree, newTermName("apply"))
      val model = Select(c.prefix.tree, newTermName("model"))

      val pairs = weakTypeOf[T].declarations.collect {
        case m: MethodSymbol if m.isCaseAccessor =>
          val name = c.literal(m.name.decoded)
          val typeClass = c.literal(m.returnType.toString)
          val value = c.Expr(Select(model, m.name))
          reify(name.splice -> (value.splice, typeClass.splice)).tree
      }

      c.Expr[Map[String, (Any, String)]](Apply(mapApply, pairs.toList))
    }
  }
}