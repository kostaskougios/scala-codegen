package com.aktit.codegen.model

import org.apache.commons.text.WordUtils

import scala.meta.Tree

/**
  * @author kostas.kougios
  *         Date: 08/09/17
  */
trait CodeEx
{
	def tree: Tree

	def syntax: String = tree.syntax

	override def toString = syntax

	override def equals(o: Any) = o match {
		case c: CodeEx => c.syntax.trim == syntax.trim
		case _ => super.equals(o)
	}

	override def hashCode = syntax.hashCode
}

object CodeEx
{

	trait Name[+T]
	{
		def name: String

		def unCapitalizedName: String = WordUtils.uncapitalize(name)

		def withName(name: String): T
	}

}