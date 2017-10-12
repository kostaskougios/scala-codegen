package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 06/10/17
  */
case class Class(
	meta: Class.Meta
) extends N
	with Method.Contains[Class]
	with Meta.Contains[Class.Meta]
	with Code
	with Code.Name[Class]
{
	override def name: String = meta.tname.value

	override def withName(name: String): Class = copy(meta = meta.copy(tname = Type.Name(name)))

	override def tree = q"..${meta.mods} class ${meta.tname}[..${meta.tparams}] ..${meta.ctorMods} (...${meta.paramss}) extends ${meta.template}"

	override def withTemplate(t: Template) = copy(meta = meta.copy(template = t))
}

object Class extends PartialParser[Class]
{

	case class Meta(
		mods: List[Mod],
		tname: Type.Name,
		tparams: List[Type.Param],
		ctorMods: List[Mod],
		paramss: List[List[Term.Param]],
		template: Template
	) extends com.aktit.macros.model.Meta with com.aktit.macros.model.Meta.Template

	override def parser = {
		case q"..$mods class $tname[..$tparams] ..$ctorMods (...$paramss) extends $template" =>
			Class(Meta(mods, tname, tparams, ctorMods, paramss, template))
	}
}