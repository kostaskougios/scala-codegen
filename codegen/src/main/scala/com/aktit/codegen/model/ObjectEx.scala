package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         08/07/19 - 23:14
  */
case class ObjectEx private(meta: ObjectEx.Meta) extends CodeEx
	with MethodEx.Contains[ObjectEx]
	with MetaEx.Contains
	with MetaEx.ContainsMods[ObjectEx]
	with CodeEx.Name[ObjectEx]
	with ValEx.Contains
	with TemplateEx.Contains[ObjectEx]
	with Extending[ObjectEx]
{
	override def tree = q"..${meta.mods.toList} object ${meta.ename} extends ${meta.template}"

	override protected def withTemplateInner(t: Template) = copy(meta = meta.copy(template = t))

	override def withMods(mods: ModsEx) = copy(
		meta = meta.copy(mods = mods.meta.mods)
	)

	override def name = meta.ename.value

	override def withName(name: String) = copy(meta = meta.copy(ename = Term.Name(name)))

	override def vals: Seq[ValEx] = template.vals
}

object ObjectEx extends PartialParser[ObjectEx]
{

	case class Meta(
		mods: Seq[Mod],
		ename: Term.Name,
		template: Template
	) extends MetaEx with MetaEx.Template with MetaEx.Mods

	override def parser = {
		case q"..$mods object $ename extends $template" =>
			ObjectEx(Meta(mods, ename, template))
	}

	def withName(name: String): ObjectEx = parser(q"object X {}").withName(name)
}
