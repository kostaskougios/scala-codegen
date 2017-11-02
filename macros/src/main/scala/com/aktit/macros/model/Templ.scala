package com.aktit.macros.model

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
	override def tree = template"{ ..$earlyStats } with ..$inits { $self => ..$stats }"
}

object Templ extends PartialParser[Templ]
{
	override def parser = {
		case template"{ ..$earlyStats } with ..$inits { $self => ..$stats }" =>
			Templ(earlyStats, inits, self, stats)
	}

	trait Contains extends Meta.Contains[Meta with Meta.Template]
	{
		def templ: Templ = Templ.parser(meta.template)
	}
}