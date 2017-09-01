package com.aktit.macros.model

import scala.meta._

/**
  * an abstract method declaration
  *
  * @author kostas.kougios
  *         Date: 31/08/17
  */
case class DeclaredMethod(
	tree: Tree,
	mods: Seq[Mod],
	termName: Term.Name,
	tparams: Seq[Type.Param],
	paramss: Seq[Seq[Term.Param]],
	tpe: Type
) extends Method
{
	def name: String = termName.value

	override def toString = tree.syntax
}
