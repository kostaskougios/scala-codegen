package com.aktit.macros.model

import com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
case class TraitEx(
	meta: TraitEx.Meta
) extends CodeEx
	with MethodEx.Contains[TraitEx]
	with MetaEx.Contains
	with MetaEx.ContainsTypeParams[TraitEx]
	with CodeEx.Name[TraitEx]
	with TemplateEx.Contains[TraitEx]
{
	def name: String = meta.tname.value

	def withName(name: String): TraitEx = copy(meta.copy(tname = scala.meta.Type.Name(name)))

	override def toString = tree.syntax

	override def withTemplate(t: Template) = copy(meta.copy(template = t))

	override def tree = q"..${meta.mods} trait ${meta.tname}[..${meta.tparams}] extends ${meta.template}"

	override def withTypeParams(params: Seq[TypeParamEx]) = copy(
		meta = meta.copy(
			tparams = params.map(_.meta.param).toList
		)
	)
}

object TraitEx extends PartialParser[TraitEx]
{

	case class Meta(
		mods: List[Mod],
		tname: scala.meta.Type.Name,
		tparams: List[scala.meta.Type.Param],
		template: Template
	) extends model.MetaEx with model.MetaEx.Template with model.MetaEx.TypeParams

	override def parser = {
		case q"..$mods trait $tname[..$tparams] extends $template" =>
			TraitEx(Meta(mods, tname, tparams, template))
	}

  def withName(name: String) = parser(q"trait X").withName(name)

}
