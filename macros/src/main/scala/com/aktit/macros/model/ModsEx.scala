package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */

case class ModsEx(meta: ModsEx.Meta)
{
    def isPrivate = meta.isPrivate

    def isProtected = meta.isProtected

    def isPublic = meta.isPublic

    def isVal = meta.isVal

    def isCase = meta.isCase
}

object ModsEx
{

    case class Meta(mods: Seq[Mod]) extends MetaEx with MetaEx.Mods

    def apply(mods: Seq[Mod]): ModsEx = ModsEx(Meta(mods))

    trait Contains extends MetaEx.Contains with MetaEx.ContainsMods
    {
        def mods: ModsEx = ModsEx(meta.mods)
    }
}
