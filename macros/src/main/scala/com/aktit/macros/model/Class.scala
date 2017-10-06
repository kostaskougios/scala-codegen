package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 06/10/17
  */
case class Class(
	mods: List[Mod],
	tname: Type.Name,
	tparams: List[Type.Param],
	ctorMods: List[Mod],
	paramss: List[List[Term.Param]],
	template: Template
) extends N with Method.Contains[Class] with Code with Code.Name[Class]
{
	override def name: String = tname.value

	override def withName(name: String): Class = copy(tname = Type.Name(name))

	override def tree = q"..$mods class $tname[..$tparams] ..$ctorMods (...$paramss) extends $template"

	override def withTemplate(t: Template) = copy(template = t)
}

object Class extends PartialParser[Class]
{
	override def parser = {
		case q"..$mods class $tname[..$tparams] ..$ctorMods (...$paramss) extends $template" =>
			Class(mods, tname, tparams, ctorMods, paramss, template)
	}
}