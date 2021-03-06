package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/09/17
  */
case class TermParamEx private(meta: TermParamEx.Meta) extends CodeEx
	with MetaEx.Contains
	with MetaEx.ContainsMods[TermParamEx]
	with CodeEx.Name[TermParamEx]
{
	override def name: String = meta.param.name.value

	override def tree = meta.param

	override def withName(name: String) = copy(
		meta = meta.copy(
			param = meta.param.copy(name = Name(name))
		)
	)

	def `type`: Option[TypeEx] = meta.param.decltpe.map(TypeEx.apply)

	def withType(tpe: String) = copy(
		meta = meta.copy(
			param = meta.param.copy(
				decltpe = Some(tpe.parse[Type].get)
			)
		)
	)

	def toVal: ValEx = {
		val p = meta.param
		val q = q"..${p.mods} val ..${List(Pat.Var(Term.Name(p.name.value)))}: ${p.decltpe.get}"
		ValEx.parser(q)
	}

	override def withMods(mods: ModsEx) = copy(
		meta = meta.copy(param = meta.param.copy(mods = mods.meta.mods.toList))
	)

}

object TermParamEx
{

	case class Meta(param: scala.meta.Term.Param) extends MetaEx with MetaEx.Mods
	{
		override def mods = param.mods
	}

	def fromSource(code: String): TermParamEx = TermParamEx(Meta(code.parse[Term.Param].get))

	def apply(param: scala.meta.Term.Param): TermParamEx = TermParamEx(Meta(param))

	// creates a string as if these params are used in a method call, i.e. "(n)(m)" for two params n,m of some type
	def toString(parameters: Seq[Seq[TermParamEx]]): String =
		if (parameters.isEmpty)
			""
		else
			parameters.map(_.map(_.name).mkString(",")).mkString("(", ")(", ")")
}