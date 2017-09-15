package com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * A method with an implementation
  *
  * @author kostas.kougios
  *         Date: 31/08/17
  */
case class DefinedMethod(
	mods: immutable.Seq[Mod],
	ename: Term.Name,
	tparams: immutable.Seq[Type.Param],
	paramss: immutable.Seq[immutable.Seq[Term.Param]],
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
	override def parser = {
		case q"..$mods def $ename[..$tparams](...$paramss): $tpeopt = $expr" =>
			DefinedMethod(mods, ename, tparams, paramss, tpeopt, expr)
	}
}
