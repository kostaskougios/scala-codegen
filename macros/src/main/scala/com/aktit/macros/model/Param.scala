package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/09/17
  */
case class Param(param: Term.Param)
{
	def withName(name: String) = copy(
		param = param.copy(name = Name(name))
	)
}
