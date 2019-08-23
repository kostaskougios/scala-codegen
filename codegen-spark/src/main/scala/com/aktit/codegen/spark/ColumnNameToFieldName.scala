package com.aktit.codegen.spark

import org.apache.commons.lang3.StringUtils

/**
  * @author kostas.kougios
  *         23/08/2019 - 22:05
  */
object ColumnNameToFieldName
{
	private val Replace = Array("\"", "(", ")", "-", "+", "_", "/", "?")
	private val ReplaceWith = Array("", "", "", "", "", "", "", "")

	def defaultColumnNameToFieldName(h: String): String = {
		val f = StringUtils.replaceEach(h, Replace, ReplaceWith)
		val a = StringUtils.split(f, ' ')
		(StringUtils.uncapitalize(a(0)) +: a.tail.map(StringUtils.capitalize)).mkString
	}

}
