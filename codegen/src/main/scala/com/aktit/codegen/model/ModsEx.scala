package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */

case class ModsEx(meta: ModsEx.Meta)
{

	import ModsEx._

	def isPrivate = meta.isPrivate

	def isProtected = meta.isProtected

	def isPublic = meta.isPublic

	def isVal = meta.isVal

	def isCase = meta.isCase

	def syntax = meta.mods.map(_.syntax).mkString(" ")

	def withCase = apply(meta.mods :+ Mod.Case())
}

object ModsEx
{

	case class Meta(mods: Seq[Mod]) extends MetaEx with MetaEx.Mods

	def apply(mods: Seq[Mod]): ModsEx = ModsEx(Meta(mods))

	def empty: ModsEx = apply(Nil)
}
