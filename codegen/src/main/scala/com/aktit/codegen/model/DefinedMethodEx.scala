package com.aktit.codegen.model

import scala.meta._

/**
  * A method with an implementation
  *
  * @author kostas.kougios
  *         Date: 31/08/17
  */
case class DefinedMethodEx(
	meta: DefinedMethodEx.Meta
) extends MethodEx[DefinedMethodEx]
	with MetaEx.Contains
	with MetaEx.ContainsMods[DefinedMethodEx]
{
	override def name: String = meta.ename.value

	override def withName(name: String) = copy(
		meta = meta.copy(
			ename = meta.ename.copy(value = name)
		)
	)

	override def parameters = meta.paramss.map(_.map(p => TermParamEx(TermParamEx.Meta(p))))

	override def withParameters(params: Seq[Seq[TermParamEx]]) = copy(
		meta = meta.copy(
			paramss = params.map(_.map(_.meta.param).toList).toList
		)
	)

	override def withReturnType(returnType: String) = copy(
		meta = meta.copy(
			tpeopt = Some(returnType.parse[Type].get)
		)
	)

	override def returnType = meta.tpeopt.map(TypeEx.apply)

	override def withImplementation(code: String) = copy(
		meta = meta.copy(
			expr = code.parse[Term].get
		)
	)

	override def toAbstract = DeclaredMethodEx.parser(q"..${meta.mods} def ${meta.ename}[..${meta.tparams}](...${meta.paramss}): ${meta.tpeopt.getOrElse(throw new IllegalStateException(s"please declare the type of this method in order to be able to convert it to it's abstract representation: $syntax"))}")

	override def tree = q"..${meta.mods} def ${meta.ename}[..${meta.tparams}](...${meta.paramss}): ${meta.tpeopt} = ${meta.expr}"

	override def withOverrides = copy(
		meta = meta.copy(
			mods = Mod.Override() +: meta.mods
		)
	)

	override def withMods(mods: ModsEx) = copy(meta = meta.copy(mods = mods.meta.mods.toList))

}

object DefinedMethodEx extends PartialParser[DefinedMethodEx]
{
	override val parser = {
		case q"..$mods def $ename[..$tparams](...$paramss): $tpeopt = $expr" =>
			DefinedMethodEx(Meta(mods, ename, tparams, paramss, tpeopt, expr))
	}

	case class Meta(
		mods: List[Mod],
		ename: Term.Name,
		tparams: List[scala.meta.Type.Param],
		paramss: List[List[Term.Param]],
		tpeopt: Option[scala.meta.Type],
		expr: Term
	) extends MetaEx with MetaEx.Mods

	def parseString(c: String): DefinedMethodEx = parser(c.parse[Stat].get)

	def withName(name: String): DefinedMethodEx = parser(q"def x:Unit={}").withName(name)
}
