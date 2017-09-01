package com.aktit.macros

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
trait ATrait
{
	def method1: String

	def method2(id: Int): String = id.toString
}
