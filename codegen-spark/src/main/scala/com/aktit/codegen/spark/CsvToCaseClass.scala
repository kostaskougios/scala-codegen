package com.aktit.codegen.spark

import java.io.FileReader

import com.aktit.codegen.model.{ClassEx, PackageEx, TermParamEx}
import com.opencsv.CSVReaderHeaderAware
import org.apache.commons.lang3.StringUtils

import scala.collection.JavaConverters.mapAsScalaMapConverter

/**
  * @author kostas.kougios
  *         13/08/2019 - 08:09
  */

private class CsvToCaseClass(targetPackage: String, newClassName: String, csvFile: String, config: CsvToCaseClassConfig)
{
	def build = {
		val reader = new FileReader(csvFile)
		try {
			val headers = new CSVReaderHeaderAware(reader)
				.readMap
				.asScala
				.keys
				.toList
				.map(headerToVariableName)
			val clz = createClass(headers)
			PackageEx.withName(targetPackage)
				.withClass(clz)
		} finally reader.close()

	}

	private def headerToVariableName(h: String) = {
		val a = StringUtils.split(h, ' ')
		val f = (StringUtils.uncapitalize(a(0)) +: a.tail.map(StringUtils.capitalize)).mkString(" ")
		StringUtils.replaceEach(f, config.replace, config.replaceWith)
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
	def createClass(targetPackage: String, newClassName: String, csvFile: String) = new Builder(targetPackage, newClassName, csvFile, CsvToCaseClassConfig.Default)

	class Builder(targetPackage: String, newClassName: String, csvFile: String, config: CsvToCaseClassConfig)
	{
		def build = new CsvToCaseClass(targetPackage, newClassName, csvFile, config).build
	}

}

case class CsvToCaseClassConfig(
	replace: Array[String] = Array(" ", "(", ")", "-", "+", "_", "/", "?"),
	replaceWith: Array[String] = Array("", "", "", "", "", "", "", "")
)

object CsvToCaseClassConfig
{
	val Default = CsvToCaseClassConfig()
}
