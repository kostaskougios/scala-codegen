package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
case class Trait(
	meta: Trait.Meta
) extends Code
	with Method.Contains[Trait]
	with Meta.Contains[Trait.Meta]
	with Code.Name[Trait]
{
	def name: String = meta.tname.value

	def withName(name: String): Trait = copy(meta.copy(tname = Type.Name(name)))

	override def toString = tree.syntax

	override def withTemplate(t: Template) = copy(meta.copy(template = t))

	override def tree = q"..${meta.mods} trait ${meta.tname}[..${meta.tparams}] extends ${meta.template}"
}

object Trait extends PartialParser[Trait]
{

	case class Meta(
		mods: List[Mod],
		tname: Type.Name,
		tparams: List[Type.Param],
		template: Template
	) extends com.aktit.macros.model.Meta with com.aktit.macros.model.Meta.Template
	override def parser = {
		case q"..$mods trait $tname[..$tparams] extends $template" =>
			Trait(Meta(mods, tname, tparams, template))
	}
}
