package com.aktit.macros.model

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
  override def toString = s"Trait($mods,$tname,$tparams,$template)"
}
