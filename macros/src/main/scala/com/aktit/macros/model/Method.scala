package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 31/08/17
  */
class Method(
	mods: Seq[Mod],
	name: Term.Name,
	tparams: Seq[Type.Param],
	paramss: Seq[Seq[Term.Param]],
	tpe: Type
)
{

	override def toString = s"Method($name,$paramss,$tpe)"
}
