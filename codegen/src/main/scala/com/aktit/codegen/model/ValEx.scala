package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
trait ValEx extends CodeEx
    with MetaEx.Contains
    with MetaEx.ContainsMods
    with CodeEx.Name[ValEx]
    with TypeEx.Contains[ValEx]
{
    override def meta: MetaEx with MetaEx.Mods
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