package com.aktit.macros.model

import com.aktit.macros.model

/**
  * @author kostas.kougios
  *         Date: 02/11/17
  */
case class TypeEx(meta: TypeEx.Meta) extends CodeEx with MetaEx.Contains
{
    override def tree = meta.tpe
}

object TypeEx
{

  case class Meta(tpe: scala.meta.Type) extends model.MetaEx

  def apply(tpe: scala.meta.Type): TypeEx = TypeEx(Meta(tpe))
}