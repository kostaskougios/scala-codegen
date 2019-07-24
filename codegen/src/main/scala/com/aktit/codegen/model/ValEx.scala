package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
trait ValEx extends CodeEx
	with MetaEx.Contains
	with MetaEx.ContainsMods[ValEx]
	with CodeEx.Name[ValEx]
	with TypeEx.Contains[ValEx]
{
	override def tree: Stat

	def toValTermParamEx: TermParamEx = TermParamEx.fromSource(syntax)

	def toMethodArgTermParamEx: TermParamEx = TermParamEx.fromSource(s"$name: ${`type`}")

	override def meta: MetaEx with MetaEx.Mods
}

object ValEx extends PartialParser[ValEx]
{
	override def parser = DeclaredValEx.parser.orElse(DefinedValEx.parser)

	def isVal(t: Tree): Boolean = parser.isDefinedAt(t)

	def fromSource(s: String): ValEx = parser(s.parse[Stat].get)

	trait Contains
	{
		def vals: Seq[ValEx]
	}

}