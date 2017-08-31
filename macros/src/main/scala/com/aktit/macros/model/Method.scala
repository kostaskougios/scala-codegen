package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 31/08/17
  */
class Method(
	tree: Tree,
	mods: Seq[Mod],
	termName: Term.Name,
	tparams: Seq[Type.Param],
	paramss: Seq[Seq[Term.Param]],
	tpe: Type
)
{
	def name: String = termName.value
	override def toString = tree.syntax
}
