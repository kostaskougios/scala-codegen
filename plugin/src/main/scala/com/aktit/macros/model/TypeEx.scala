package com.aktit.macros.model

import com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 02/11/17
  */
case class TypeEx(meta: TypeEx.Meta) extends CodeEx with MetaEx.Contains
{
    def name: String = meta.tpe match {
        case n: Type.Name => n.value
    }

    override def tree = meta.tpe
}

object TypeEx
{

    case class Meta(tpe: Type) extends model.MetaEx

    def apply(name: String): TypeEx = TypeEx(Meta(Type.Name(name)))

    def apply(tpe: Type): TypeEx = TypeEx(Meta(tpe))

    trait Contains[+T]
    {
        def `type`: TypeEx

        def withType(t: TypeEx): T
    }

}