package com.aktit.macros.model

import scala.meta._

/**
  * an abstract method declaration
  *
  * @author kostas.kougios
  *         Date: 31/08/17
  */
case class DeclaredMethod(
	mods: List[Mod],
	ename: Term.Name,
	tparams: List[Type.Param],
	paramss: List[List[Term.Param]],
	tpe: Type
) extends Method
{
	def name: String = ename.value

	override def withName(name: String) = copy(
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

	//	scala.meta.parsers.Parse.parse
	//import scala.meta.
	def code(c: String): DeclaredMethod = parser(c.parse[Stat].get)

	def noArgReturningUnit(name: String) = DeclaredMethod(Nil, Term.Name(name), Nil, Nil, Type.Name("Unit"))
}