package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
trait MethodEx[A] extends CodeEx
	with CodeEx.Name[MethodEx[A]]
	with MetaEx.Contains
	with MetaEx.ContainsMods[A]
{
	def withReturnType(returnType: String): MethodEx[A]

	def returnType: Option[TypeEx]

	def parameters: Seq[Seq[TermParamEx]]

	def withParameter(param: TermParamEx): MethodEx[A] = withParameters(Seq(Seq(param)))

	def withParameter(src: String): MethodEx[A] = withParameters(Seq(Seq(TermParamEx.fromSource(src))))

	def withParameters(params: Seq[Seq[TermParamEx]]): MethodEx[A]

	// converts this method to it's abstract (no impl) representation
	def toAbstract: DeclaredMethodEx

	// adds impl (or replaces the existing one)
	def withImplementation(code: String): DefinedMethodEx

	def withOverrides: MethodEx[A]

	override def tree: Stat
}

object MethodEx extends PartialParser[MethodEx[_]]
{
	override val parser = DeclaredMethodEx.parser.orElse(DefinedMethodEx.parser)

	def fromSource(s: String): MethodEx[_] = parser(s.parse[Stat].get)

	def isMethod(t: Tree): Boolean = parser.isDefinedAt(t)

	trait Contains[T] extends TemplateEx.Contains[T]
	{
		def meta: MetaEx with MetaEx.Template

		def methods: Seq[MethodEx[_]] = meta.template.children.collect(parser)

		def declaredMethods: Seq[DeclaredMethodEx] = methods.collect {
			case d: DeclaredMethodEx => d
		}

		def definedMethods: Seq[DefinedMethodEx] = methods.collect {
			case d: DefinedMethodEx => d
		}

		def withMethods(methods: Seq[MethodEx[_]]): T = {
			withTemplateInner(
				Template(
					meta.template.early,
					meta.template.inits,
					meta.template.self,
					meta.template.stats.filterNot(isMethod) ++ methods.map(_.tree)
				)
			)
		}

		def withVals(vals: Seq[ValEx]): T = {
			withTemplateInner(
				Template(
					meta.template.early,
					meta.template.inits,
					meta.template.self,
					meta.template.stats.filterNot(ValEx.isVal) ++ vals.map(_.tree)
				)
			)
		}

	}

}
