package com.aktit.codegen.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 12/10/17
  */
trait MetaEx

object MetaEx
{

	trait Contains
	{
		def meta: MetaEx
	}

	trait ContainsMods
	{
		def meta: MetaEx with Mods

		def mods: ModsEx = ModsEx(meta.mods)

		def isPrivate = mods.isPrivate

		def isProtected = mods.isProtected

		def isPublic: Boolean = mods.isPublic

		def isVal: Boolean = mods.isVal

		def isCase: Boolean = mods.isCase
	}

	trait ContainsTypeParams[T]
	{
		def meta: MetaEx with TypeParams

		def typeParams: immutable.Seq[TypeParamEx] = meta.tparams.map(TypeParamEx.apply)

		def withTypeParams(params: Seq[TypeParamEx]): T

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

		def isVal: Boolean = mods.exists {
			case _: Mod.ValParam => true
			case _ => false
		}

		def isCase: Boolean = mods.exists {
			case _: Mod.Case => true
			case _ => false
		}
	}

}