package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 03/11/17
  */
case class TypeParamEx private(meta: TypeParamEx.Meta) extends CodeEx with MetaEx.Contains
{
	def toType: TypeEx = {
		val typeArgs = meta.param.tparams.map(_.name.value)
		val ta = if (typeArgs.isEmpty) "" else s"[${typeArgs.mkString(",")}]"
		TypeEx(
			s"${meta.param.name} $ta".parse[scala.meta.Type].get
		)
	}

	override def tree = meta.param
}

object TypeParamEx
{

	case class Meta(
		param: scala.meta.Type.Param
	) extends MetaEx

	def apply(param: scala.meta.Type.Param): TypeParamEx = TypeParamEx(Meta(param))
}
