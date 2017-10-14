package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/09/17
  */
case class Param(meta: Param.Meta) extends Code
	with Meta.Contains[Param.Meta]
	with Code.Name[Param]
{
	override def name: String = meta.param.name.value

	override def tree = meta.param

	override def withName(name: String) = copy(
		meta = meta.copy(
			param = meta.param.copy(name = Name(name))
		)
	)

	def withType(tpe: String) = copy(
		meta = meta.copy(
			param = meta.param.copy(
			decltpe = Some(Type.Name(tpe))
			)
		)
	)
}

object Param
{

	case class Meta(param: Term.Param) extends com.aktit.macros.model.Meta

	def parseString(code: String) = Param(Meta(code.parse[Term.Param].get))
}