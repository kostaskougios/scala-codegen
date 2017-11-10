package com.aktit.macros.model

import com.aktit.macros.model.MetaEx.Mods

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
trait ValEx extends CodeEx
    with MetaEx.ContainsMods
    with MetaEx.Contains
    with CodeEx.Name[ValEx]
    with TypeEx.Contains[ValEx]
{
    override def meta: MetaEx with Mods
}

object ValEx extends PartialParser[ValEx]
{
    override def parser = ValDeclaredEx.parser.orElse(ValDefinedEx.parser)

    def fromSource(s: String): ValEx = parser(s.parse[Stat].get)

    trait Contains
    {
        def vals: Seq[ValEx]
    }
}