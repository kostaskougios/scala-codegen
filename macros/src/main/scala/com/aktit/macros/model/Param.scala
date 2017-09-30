package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/09/17
  */
case class Param(param: Term.Param)
{
	def name: String = param.name.value

	def withName(name: String) = copy(
		param = param.copy(name = Name(name))
	)

	def withType(tpe: String) = copy(
		param = param.copy(
			decltpe = Some(Type.Name(tpe))
		)
	)
}

object Param
{
	def parseString(code: String) = Param(code.parse[Term.Param].get)
}