package com.aktit.macros.model

import com.aktit.macros.model

import scala.meta._

/**
  * an abstract method declaration
  *
  * @author kostas.kougios
  *         Date: 31/08/17
  */
case class DeclaredMethodEx(
	meta: DeclaredMethodEx.Meta
) extends MethodEx
	with MetaEx.Contains
	with MetaEx.ContainsMods
{
	def name: String = meta.ename.value

	override def withName(name: String) = copy(
		meta = meta.copy(
			ename = meta.ename.copy(value = name)
		)
	)

	override def withParameters(params: Seq[Seq[TermParamEx]]) = copy(
		meta = meta.copy(
			paramss = params.map(_.map(_.meta.param).toList).toList
		)
	)

	override def tree = q"..${meta.mods} def ${meta.ename}[..${meta.tparams}](...${meta.paramss}): ${meta.tpe}"

	override def parameters: Seq[Seq[TermParamEx]] = meta.paramss.map(_.map(p => TermParamEx(TermParamEx.Meta(p))))

	override def withReturnType(returnType: String) = copy(
		meta = meta.copy(
			tpe = scala.meta.Type.Name(returnType)
		)
	)

	override def toAbstract = this

	override def withImplementation(code: String) = DefinedMethodEx.parser(
		q"..${meta.mods} def ${meta.ename}[..${meta.tparams}](...${meta.paramss}): ${meta.tpe} = ${code.parse[Term].get}"
	)

    override def withOverrides = copy(
        meta = meta.copy(
            mods = Mod.Override() +: meta.mods
        )
    )
}

object DeclaredMethodEx extends PartialParser[DeclaredMethodEx]
{
	override val parser = {
		case q"..$mods def $ename[..$tparams](...$paramss): $tpe" =>
			DeclaredMethodEx(Meta(mods, ename, tparams, paramss, tpe))
	}

	case class Meta(
		mods: List[Mod],
		ename: Term.Name,
		tparams: List[scala.meta.Type.Param],
		paramss: List[List[Term.Param]],
		tpe: scala.meta.Type
	) extends model.MetaEx with model.MetaEx.Mods

	def parseString(c: String): DeclaredMethodEx = parser(c.parse[Stat].get)

	def noArgReturningUnit(name: String): DeclaredMethodEx = DeclaredMethodEx(Meta(Nil, Term.Name(name), Nil, Nil, scala.meta.Type.Name("Unit")))
}