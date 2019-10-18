package com.aktit.codegen.spark

import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.types.StructField

/**
  * @author kostas.kougios
  *         23/08/2019 - 22:05
  */
object Naming
{
	private val Replace = Array("\"", "(", ")", "-", "+", "_", "/", "?")
	private val ReplaceWith = Array("", "", "", "", "", "", "", "")

	def defaultFieldToFieldName(field: StructField): String = {
		val f = StringUtils.replaceEach(field.name, Replace, ReplaceWith)
		val a = StringUtils.split(f, ' ')
		(StringUtils.uncapitalize(a(0)) +: a.tail.map(StringUtils.capitalize)).mkString
	}

	def defaultFieldToClassName(field: StructField): String = {
		val f = StringUtils.replaceEach(field.name, Replace, ReplaceWith)
		val a = StringUtils.split(f, ' ')
		(StringUtils.capitalize(a(0)) +: a.tail.map(StringUtils.capitalize)).mkString
	}
}
