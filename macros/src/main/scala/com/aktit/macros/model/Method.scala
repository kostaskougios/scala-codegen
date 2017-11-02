package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
trait Method extends Code
	with Code.Name[Method]
{
	def withReturnType(returnType: String): Method

	def parameters: Seq[Seq[Param]]

	def withParameters(params: Seq[Seq[Param]]): Method

	// converts this method to it's abstract (no impl) representation
	def toAbstract: DeclaredMethod

	// adds impl (or replaces the existing one)
	def withImplementation(code: String): DefinedMethod

	override def tree: Stat
}

object Method extends PartialParser[Method]
{
	override val parser = DeclaredMethod.parser.orElse(DefinedMethod.parser)

	def isMethod(t: Tree): Boolean = parser.isDefinedAt(t)

	trait Contains[T]
	{
		def meta: Meta.Template

		protected def withTemplate(t: Template): T

		def methods: Seq[Method] = meta.template.children.collect(parser)

		def declaredMethods: Seq[DeclaredMethod] = methods.collect {
			case d: DeclaredMethod => d
		}

		def definedMethods: Seq[DefinedMethod] = methods.collect {
			case d: DefinedMethod => d
		}

		def withMethods(methods: Seq[Method]): T = {
			withTemplate(
				Template(
					meta.template.early,
					meta.template.inits,
					meta.template.self,
					meta.template.stats.filterNot(isMethod) ++ methods.map(_.tree)
				)
			)
		}

	}

}
