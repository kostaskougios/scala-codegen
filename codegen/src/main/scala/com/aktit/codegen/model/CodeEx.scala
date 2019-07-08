package com.aktit.codegen.model

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
		case c: CodeEx => c.syntax == syntax
		case _ => super.equals(o)
	}

	override def hashCode = syntax.hashCode
}

object CodeEx
{

	trait Name[+T]
	{
		def name: String

		def withName(name: String): T
	}

}