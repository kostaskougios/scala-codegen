package com.aktit.macros.model

import com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 03/11/17
  */
case class TypeParamEx(meta: TypeParamEx.Meta) extends CodeEx with MetaEx.Contains
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
    ) extends model.MetaEx

    def apply(param: scala.meta.Type.Param): TypeParamEx = TypeParamEx(Meta(param))
}
