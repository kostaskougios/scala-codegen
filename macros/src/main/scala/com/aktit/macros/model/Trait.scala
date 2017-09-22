package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
case class Trait(
	tree: Tree,
	mods: Seq[Mod],
	tname: Type.Name,
	tparams: Seq[Type.Param],
	template: Template
) extends N with Method.Contains[Trait]
{
	def name: String = tname.value

	override def toString = tree.syntax

	override def withTemplate(t: Template) = copy(template = t)
}

object Trait extends PartialParser[Trait]
{
	override def parser = {
		case tree@q"..$mods trait $tname[..$tparams] extends $template" =>
			Trait(tree, mods, tname, tparams, template)
	}
}
