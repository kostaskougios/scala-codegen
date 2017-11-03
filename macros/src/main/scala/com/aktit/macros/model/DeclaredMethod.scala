package com.aktit.macros.model

import com.aktit.macros.model

import scala.meta._

/**
  * an abstract method declaration
  *
  * @author kostas.kougios
  *         Date: 31/08/17
  */
case class DeclaredMethod(
	meta: DeclaredMethod.Meta
) extends Method
	with Meta.Contains[DeclaredMethod.Meta]
	with Meta.ContainsMods
{
	def name: String = meta.ename.value

	override def withName(name: String) = copy(
		meta = meta.copy(
			ename = meta.ename.copy(value = name)
		)
	)

	override def withParameters(params: Seq[Seq[Param]]) = copy(
		meta = meta.copy(
			paramss = params.map(_.map(_.meta.param).toList).toList
		)
	)

	override def tree = q"..${meta.mods} def ${meta.ename}[..${meta.tparams}](...${meta.paramss}): ${meta.tpe}"

	override def parameters: Seq[Seq[Param]] = meta.paramss.map(_.map(p => Param(Param.Meta(p))))

	override def withReturnType(returnType: String) = copy(
		meta = meta.copy(
			tpe = scala.meta.Type.Name(returnType)
		)
	)

	override def toAbstract = this

	override def withImplementation(code: String) = DefinedMethod.parser(
		q"..${meta.mods} def ${meta.ename}[..${meta.tparams}](...${meta.paramss}): ${meta.tpe} = ${code.parse[Term].get}"
	)
}

object DeclaredMethod extends PartialParser[DeclaredMethod]
{
	override val parser = {
		case q"..$mods def $ename[..$tparams](...$paramss): $tpe" =>
			DeclaredMethod(Meta(mods, ename, tparams, paramss, tpe))
	}

	case class Meta(
		mods: List[Mod],
		ename: Term.Name,
		tparams: List[scala.meta.Type.Param],
		paramss: List[List[Term.Param]],
		tpe: scala.meta.Type
	) extends model.Meta with model.Meta.Mods

	def parseString(c: String): DeclaredMethod = parser(c.parse[Stat].get)

	def noArgReturningUnit(name: String): DeclaredMethod = DeclaredMethod(Meta(Nil, Term.Name(name), Nil, Nil, scala.meta.Type.Name("Unit")))
}