package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 12/10/17
  */
trait Meta

object Meta
{

	trait Contains[+T <: Meta]
	{
		def meta: T
	}

	trait ContainsMods
	{
		def meta: Meta with Mods

		def isPrivate = meta.isPrivate

		def isProtected = meta.isProtected

		def isPublic = meta.isPublic
	}

	trait Template
	{
		def template: scala.meta.Template
	}

	trait Mods
	{
		def mods: Seq[Mod]

		def isPrivate = mods.collect {
			case mod"private[$_]" => true
		}.nonEmpty

		def isProtected = mods.collect {
			case mod"protected[$_]" => true
		}.nonEmpty

		def isPublic: Boolean = !isPrivate && !isProtected
	}
}