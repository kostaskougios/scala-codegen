package com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 09/10/17
  */
case class TemplateEx(
	earlyStats: List[Stat],
	inits: List[Init],
	self: Self,
	stats: List[Stat]
) extends CodeEx with ValEx.Contains
{
	override def tree: Template = template"{ ..$earlyStats } with ..$inits { $self => ..$stats }"

	override def vals = stats.collect(ValEx.parser)
}

object TemplateEx extends PartialParser[TemplateEx]
{
	override def parser = {
		case template"{ ..$earlyStats } with ..$inits { $self => ..$stats }" =>
			TemplateEx(earlyStats, inits, self, stats)
	}

	trait Contains[S]
	{
		def meta: MetaEx with MetaEx.Template

		protected def withTemplate(t: Template): S

		def templ: TemplateEx = TemplateEx.parser(meta.template)

		def withTempl(t: TemplateEx) = withTemplate(t.tree)

		def extendsTypes: immutable.Seq[TypeEx] = templ.inits.map(_.tpe).map(TypeEx.apply)
	}
}