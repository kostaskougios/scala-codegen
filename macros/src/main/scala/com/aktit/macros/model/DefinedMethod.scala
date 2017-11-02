package com.aktit.macros.model

import scala.meta._

/**
  * A method with an implementation
  *
  * @author kostas.kougios
  *         Date: 31/08/17
  */
case class DefinedMethod(
	meta: DefinedMethod.Meta
) extends Method
	with Meta.Contains[DefinedMethod.Meta]
{
	override def name: String = meta.ename.value

	override def withName(name: String) = copy(
		meta = meta.copy(
			ename = meta.ename.copy(value = name)
		)
	)

	override def parameters = meta.paramss.map(_.map(p => Param(Param.Meta(p))))

	override def withParameters(params: Seq[Seq[Param]]) = copy(
		meta = meta.copy(
			paramss = params.map(_.map(_.meta.param).toList).toList
		)
	)

	override def withReturnType(returnType: String) = copy(
		meta = meta.copy(
		tpeopt = Some(Type.Name(returnType))
		)
	)

	override def withImplementation(code: String) = copy(
		meta = meta.copy(
		expr = code.parse[Term].get
		)
	)

	override def toAbstract = DeclaredMethod.parser(q"..${meta.mods} def ${meta.ename}[..${meta.tparams}](...${meta.paramss}): ${meta.tpeopt.getOrElse(throw new IllegalStateException(s"please declare the type of this method in order to be able to convert it to it's abstract representation: $syntax"))}")

	override def tree = q"..${meta.mods} def ${meta.ename}[..${meta.tparams}](...${meta.paramss}): ${meta.tpeopt} = ${meta.expr}"
}

object DefinedMethod extends PartialParser[DefinedMethod]
{
	override val parser = {
		case q"..$mods def $ename[..$tparams](...$paramss): $tpeopt = $expr" =>
			DefinedMethod(Meta(mods, ename, tparams, paramss, tpeopt, expr))
	}

	case class Meta(
		mods: List[Mod],
		ename: Term.Name,
		tparams: List[Type.Param],
		paramss: List[List[Term.Param]],
		tpeopt: Option[Type],
		expr: Term
	) extends com.aktit.macros.model.Meta

	def parseString(c: String): DefinedMethod = parser(c.parse[Stat].get)

	def noArgReturningUnit(name: String): DefinedMethod = parser(q"def x:Unit={}").withName(name)
}
