package com.aktit.macros.model

import scala.meta._

/**
  * A method with an implementation
  *
  * @author kostas.kougios
  *         Date: 31/08/17
  */
case class DefinedMethod(
	tree: Tree,
	mods: Seq[Mod],
	termName: Term.Name,
	tparams: Seq[Type.Param],
	paramss: Seq[Seq[Term.Param]],
	tpe: Option[Type],
	expr: Term
) extends Method
{
	def name: String = termName.value

	override def toString = tree.syntax
}

object DefinedMethod extends PartialParser[DefinedMethod]
{
	override def parser = {
		case tree@q"..$mods def $ename[..$tparams](...$paramss): $tpeopt = $expr" =>
			DefinedMethod(tree, mods, ename, tparams, paramss, tpeopt, expr)
	}
}
