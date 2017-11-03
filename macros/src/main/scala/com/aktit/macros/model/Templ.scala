package com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 09/10/17
  */
case class Templ(
	earlyStats: List[Stat],
	inits: List[Init],
	self: Self,
	stats: List[Stat]
) extends Code
{
	override def tree: Template = template"{ ..$earlyStats } with ..$inits { $self => ..$stats }"
}

object Templ extends PartialParser[Templ]
{
	override def parser = {
		case template"{ ..$earlyStats } with ..$inits { $self => ..$stats }" =>
			Templ(earlyStats, inits, self, stats)
	}

	trait Contains[S]
	{
		def meta: Meta with Meta.Template

		protected def withTemplate(t: Template): S

		def templ: Templ = Templ.parser(meta.template)

		def withTempl(t: Templ) = withTemplate(t.tree)

		def extendsTypes: immutable.Seq[Type] = templ.inits.map(_.tpe).map(Type.apply)
	}
}