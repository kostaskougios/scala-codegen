package com.aktit.macros.model

import com.aktit.macros.model

/**
  * @author kostas.kougios
  *         Date: 03/11/17
  */
case class TypeParam(meta: TypeParam.Meta) extends Code with Meta.Contains
{
  override def tree = meta.param
}

object TypeParam
{

  case class Meta(
    param: scala.meta.Type.Param
  ) extends model.Meta

  def apply(param: scala.meta.Type.Param): TypeParam = TypeParam(Meta(param))
}
