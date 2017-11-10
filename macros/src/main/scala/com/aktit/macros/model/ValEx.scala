package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
case class ValEx(meta: ValEx.Meta)
    extends CodeEx
        with MetaEx.ContainsMods
        with MetaEx.Contains
        with CodeEx.Name[ValEx]
        with TypeEx.Contains[ValEx]
{
    override def tree = q"..${meta.mods} val ..${meta.patsnel}: ${meta.tpe}"

    override def name = meta.patsnel.collectFirst {
        case n: Pat.Var => n.name.value
    }.get

    override def withName(name: String) = copy(
        meta = meta.copy(
            patsnel = List(Pat.Var(Term.Name(name)))
        )
    )

    override def `type` = TypeEx.apply(meta.tpe)

    override def withType(t: TypeEx) = copy(
        meta = meta.copy(
            tpe = t.meta.tpe
        )
    )
}

object ValEx extends PartialParser[ValEx]
{

    case class Meta(mods: List[Mod], patsnel: List[scala.meta.Pat], tpe: Type) extends MetaEx with MetaEx.Mods

    override def parser = {
        case q"..$mods val ..$patsnel: $tpe" =>
            ValEx(Meta(mods, patsnel, tpe))
    }

    trait Contains
    {
        def vals: Seq[ValEx]
    }

}
