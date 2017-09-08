package com.aktit.macros.model

import scala.meta.Tree

/**
  * @author kostas.kougios
  *         Date: 08/09/17
  */
trait PartialParser[T]
{
	def parser: PartialFunction[Tree, T]
}
