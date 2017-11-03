package com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 12/10/17
  */
trait Meta

object Meta
{

	trait Contains
	{
		def meta: Meta
	}

	trait ContainsMods
	{
		def meta: Meta with Mods

		def isPrivate = meta.isPrivate

		def isProtected = meta.isProtected

		def isPublic = meta.isPublic
	}

	trait ContainsTypeParams[T]
	{
		def meta: Meta with TypeParams

		def typeParams: immutable.Seq[TypeParam] = meta.tparams.map(TypeParam.apply)

		def withTypeParams(params: Seq[TypeParam]): T
	}

	trait TypeParams
	{
		def tparams: List[scala.meta.Type.Param]
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