package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
class Package(name: Term.Ref, topstats: Seq[Stat])
{

  override def toString = s"Package($name)"
}
