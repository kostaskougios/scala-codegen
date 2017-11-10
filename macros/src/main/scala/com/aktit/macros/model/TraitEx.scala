package com.aktit.macros.model

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
	with MetaEx.ContainsMods
	with MetaEx.ContainsTypeParams[TraitEx]
	with CodeEx.Name[TraitEx]
	with TemplateEx.Contains[TraitEx]
	with ModsEx.Contains
{
	def name: String = meta.tname.value

	def withName(name: String): TraitEx = copy(meta.copy(tname = scala.meta.Type.Name(name)))

	override def toString = tree.syntax

	override protected def withTemplateInner(t: Template) = copy(meta.copy(template = t))

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
	) extends MetaEx with MetaEx.Template with MetaEx.TypeParams with MetaEx.Mods

	override def parser = {
		case q"..$mods trait $tname[..$tparams] extends $template" =>
			TraitEx(Meta(mods, tname, tparams, template))
	}

	def withName(name: String): TraitEx = parser(q"trait X").withName(name)

}
