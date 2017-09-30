package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
trait Method extends Code
{
	def name: String

	def withName(name: String): Method

	def parameters: Seq[Seq[Param]]

	def withParameters(params: Seq[Seq[Param]]): Method

	override def tree: Stat
}

object Method extends PartialParser[Method]
{
	override val parser = DeclaredMethod.parser.orElse(DefinedMethod.parser)

	def isMethod(t: Tree): Boolean = parser.isDefinedAt(t)

	trait Contains[T]
	{
		def template: Template

		def withTemplate(t: Template): T

		def methods: Seq[Method] = template.children.collect(parser)

		def declaredMethods: Seq[DeclaredMethod] = methods.collect {
			case d: DeclaredMethod => d
		}

		def definedMethods: Seq[DefinedMethod] = methods.collect {
			case d: DefinedMethod => d
		}

		def withMethods(methods: Seq[Method]): T = {
			withTemplate(
				Template(
					template.early,
					template.inits,
					template.self,
					template.stats.filterNot(isMethod) ++ methods.map(_.tree)
				)
			)
		}

	}

}
