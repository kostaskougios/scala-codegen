package com.aktit.macros.model

import scala.meta.Tree

/**
  * @author kostas.kougios
  *         Date: 08/09/17
  */
trait Code
{
	def tree: Tree

	def syntax: String = tree.syntax

	override def toString = tree.syntax
}
