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

	def withCase = apply(meta.mods :+ mod"case")

	def withPrivate = apply(meta.mods :+ mod"private")

	def withProtected = apply(meta.mods :+ mod"protected")

	def withVal = apply(meta.mods :+ mod"valparam")

	def removeVal = apply(meta.mods.filterNot {
		case mod"valparam" => true
		case _ => false
	})

	def withImplicit = apply(meta.mods :+ mod"implicit")

	def withFinal = apply(meta.mods :+ mod"final")

	def withOverride = apply(meta.mods :+ mod"override")
}

object ModsEx
{

	case class Meta(mods: Seq[Mod]) extends MetaEx with MetaEx.Mods

	def apply(mods: Seq[Mod]): ModsEx = ModsEx(Meta(mods))

	def empty: ModsEx = apply(Nil)
}
