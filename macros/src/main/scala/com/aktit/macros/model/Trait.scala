package com.aktit.macros.model

import com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
case class Trait(
	meta: Trait.Meta
) extends Code
	with Method.Contains[Trait]
	with Meta.Contains
	with Meta.ContainsTypeParams[Trait]
	with Code.Name[Trait]
	with Templ.Contains[Trait]
{
	def name: String = meta.tname.value

	def withName(name: String): Trait = copy(meta.copy(tname = scala.meta.Type.Name(name)))

	override def toString = tree.syntax

	override def withTemplate(t: Template) = copy(meta.copy(template = t))

	override def tree = q"..${meta.mods} trait ${meta.tname}[..${meta.tparams}] extends ${meta.template}"

	override def withTypeParams(params: Seq[TypeParam]) = copy(
		meta = meta.copy(
			tparams = params.map(_.meta.param).toList
		)
	)
}

object Trait extends PartialParser[Trait]
{

	case class Meta(
		mods: List[Mod],
		tname: scala.meta.Type.Name,
		tparams: List[scala.meta.Type.Param],
		template: Template
	) extends model.Meta with model.Meta.Template with model.Meta.TypeParams

	override def parser = {
		case q"..$mods trait $tname[..$tparams] extends $template" =>
			Trait(Meta(mods, tname, tparams, template))
	}

  def withName(name: String) = parser(q"trait X").withName(name)

}
