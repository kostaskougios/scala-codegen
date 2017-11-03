package com.aktit.macros.model

import com.aktit.macros.model

/**
  * @author kostas.kougios
  *         Date: 02/11/17
  */
case class Type(meta: Type.Meta) extends Meta.Contains
{
}

object Type
{

  case class Meta(tpe: scala.meta.Type) extends model.Meta

  def apply(tpe: scala.meta.Type): Type = Type(Meta(tpe))
}