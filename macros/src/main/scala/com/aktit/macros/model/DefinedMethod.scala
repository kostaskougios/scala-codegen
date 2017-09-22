package com.aktit.macros.model

import scala.meta._

/**
  * A method with an implementation
  *
  * @author kostas.kougios
  *         Date: 31/08/17
  */
case class DefinedMethod(
	mods: List[Mod],
	ename: Term.Name,
	tparams: List[Type.Param],
	paramss: List[List[Term.Param]],
	tpeopt: Option[Type],
	expr: Term
) extends Method
{
	override def name: String = ename.value

	override def withName(name: String) = copy(
		ename = ename.copy(value = name)
	)

	override def tree = q"..$mods def $ename[..$tparams](...$paramss): $tpeopt = $expr"

	override def code = tree.syntax
}

object DefinedMethod extends PartialParser[DefinedMethod]
{
	override val parser = {
		case q"..$mods def $ename[..$tparams](...$paramss): $tpeopt = $expr" =>
			DefinedMethod(mods, ename, tparams, paramss, tpeopt, expr)
	}
}
