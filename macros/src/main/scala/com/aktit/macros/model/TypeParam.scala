package com.aktit.macros.model

import com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 03/11/17
  */
case class TypeParam(meta: TypeParam.Meta) extends Code with Meta.Contains
{
  def toType = {
    val typeArgs = meta.param.tparams.map(_.name.value)
    val ta = if (typeArgs.isEmpty) "" else s"[${typeArgs.mkString(",")}]"
    Type(
      s"${meta.param.name}".parse[scala.meta.Type].get
    )
  }

  override def tree = meta.param
}

object TypeParam
{

  case class Meta(
    param: scala.meta.Type.Param
  ) extends model.Meta

  def apply(param: scala.meta.Type.Param): TypeParam = TypeParam(Meta(param))
}
