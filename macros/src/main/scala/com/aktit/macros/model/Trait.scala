package com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
class Trait(
  mods: Seq[Mod],
  tname: Type.Name,
  tparams: Seq[Type.Param],
  template: Template
) extends N
{
	def methods: immutable.Seq[Method] = template.children.collect {
		case q"..$mods def $ename[..$tparams](...$paramss): $tpe" =>
			new Method(mods, ename, tparams, paramss, tpe)

	}
  override def toString = s"Trait($mods,$tname,$tparams,$template)"
}
