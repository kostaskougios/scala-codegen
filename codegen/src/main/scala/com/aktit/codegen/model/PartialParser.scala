package com.aktit.codegen.model

import scala.meta.Tree

/**
  * @author kostas.kougios
  *         Date: 08/09/17
  */
trait PartialParser[T]
{
	/**
	  * @return a partial function that might parse a Tree
	  */
	def parser: PartialFunction[Tree, T]
}
