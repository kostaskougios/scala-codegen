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

	override def withParameters(params: Seq[Seq[Param]]) = copy(
		paramss = params.map(_.map(_.param).toList).toList
	)

	override def tree = q"..$mods def $ename[..$tparams](...$paramss): $tpe"

	override def parameters: Seq[Seq[Param]] = paramss.map(_.map(Param.apply))

	override def withReturnType(returnType: String) = copy(
		tpe = Type.Name(returnType)
	)
}

object DeclaredMethod extends PartialParser[DeclaredMethod]
{
	override val parser = {
		case q"..$mods def $ename[..$tparams](...$paramss): $tpe" =>
			DeclaredMethod(mods, ename, tparams, paramss, tpe)
	}

	def parseString(c: String): DeclaredMethod = parser(c.parse[Stat].get)

	def noArgReturningUnit(name: String) = DeclaredMethod(Nil, Term.Name(name), Nil, Nil, Type.Name("Unit"))
}