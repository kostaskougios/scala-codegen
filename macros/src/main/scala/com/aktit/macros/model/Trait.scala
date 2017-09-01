package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
case class Trait(
	tree: Tree,
	mods: Seq[Mod],
	tname: Type.Name,
	tparams: Seq[Type.Param],
	template: Template
) extends N with Method.Contains
{
	def name: String = tname.value

	override def toString = tree.syntax
}
