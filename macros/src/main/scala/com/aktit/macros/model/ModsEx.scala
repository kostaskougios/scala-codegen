package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
object ModsEx
{
    def isPrivate(mods: Seq[Mod]) = mods.collect {
        case mod"private[$_]" => true
    }.nonEmpty

    def isProtected(mods: Seq[Mod]) = mods.collect {
        case mod"protected[$_]" => true
    }.nonEmpty

    def isPublic(mods: Seq[Mod]): Boolean = !isPrivate(mods) && !isProtected(mods)

}
