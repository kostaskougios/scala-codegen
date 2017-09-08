package com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * an abstract method declaration
  *
  * @author kostas.kougios
  *         Date: 31/08/17
  */
case class DeclaredMethod(
	mods: immutable.Seq[Mod],
	ename: Term.Name,
	tparams: immutable.Seq[Type.Param],
	paramss: immutable.Seq[immutable.Seq[Term.Param]],
	tpe: Type
) extends Method
{
	def name: String = ename.value

	def withName(name: String) = copy(
		ename = ename.copy(value = name)
	)

	override def code = tree.syntax

	override def tree = q"..$mods def $ename[..$tparams](...$paramss): $tpe"
}

object DeclaredMethod extends PartialParser[DeclaredMethod]
{
	override def parser = {
		case q"..$mods def $ename[..$tparams](...$paramss): $tpe" =>
			DeclaredMethod(mods, ename, tparams, paramss, tpe)
	}
}