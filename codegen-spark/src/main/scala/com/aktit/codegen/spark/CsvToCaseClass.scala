package com.aktit.codegen.spark

import java.io.File

import com.aktit.codegen.model.{ClassEx, PackageEx, TermParamEx}

import scala.io.Source

/**
  * @author kostas.kougios
  *         13/08/2019 - 08:09
  */

private class CsvToCaseClass(
	targetPackage: String,
	newClassName: String,
	csvFile: String,
	headerToVariableName: String => String
)
{
	def build = {
		val source = Source.fromFile(new File(csvFile))
		try {
			val headers = source.getLines.next
				.split(",")
				.map(_.trim)
				.map(headerToVariableName)
			val clz = createClass(headers)
			PackageEx.withName(targetPackage)
				.withClass(clz)
		} finally source.close()

	}

	private def createClass(headers: Seq[String]) = {
		val vals = headers.map {
			h =>
				TermParamEx.fromSource(s"$h : String")
		}
		ClassEx.withName(newClassName)
			.withConstructorParameters(vals)
	}
}

object CsvToCaseClass
{
	def createClass(
		targetPackage: String,
		newClassName: String,
		csvFile: String,
		headerToVariableName: String => String = ColumnNameToFieldName.defaultColumnNameToFieldName
	) = new CsvToCaseClass(targetPackage, newClassName, csvFile, headerToVariableName).build
}
