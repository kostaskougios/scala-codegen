package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
case class Trait(
	mods: List[Mod],
	tname: Type.Name,
	tparams: List[Type.Param],
	template: Template
) extends N with Method.Contains[Trait] with Code
{
	def name: String = tname.value

	override def toString = tree.syntax

	override def withTemplate(t: Template) = copy(template = t)

	override def code = tree.syntax

	override def tree = q"..$mods trait $tname[..$tparams] extends $template"
}

object Trait extends PartialParser[Trait]
{
	override def parser = {
		case q"..$mods trait $tname[..$tparams] extends $template" =>
			Trait(mods, tname, tparams, template)
	}
}
