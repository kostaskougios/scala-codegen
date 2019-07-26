package com.aktit.codegen.patterns

import com.aktit.codegen.model._
import org.apache.commons.lang3.StringUtils

/**
  * @author kostas.kougios
  *         24/07/19 - 22:24
  */
private class Reflect(
	pckg: PackageEx,
	classFilter: ClassEx => Boolean,
	fieldClass: String
)
{
	private val fieldClzName = StringUtils.substringAfterLast(fieldClass, ".")
	def reflect = {
		val reflects = pckg.classes
			.filter(classFilter)
			.map(generateReflect)
		PackageEx.withName(pckg.name)
			.withImports(pckg.imports :+ ImportEx.fromSource(s"import $fieldClass"))
			.withObjects(reflects)
	}

	private def generateReflect(clz: ClassEx) = {
		val fields = clz.vals.map {
			v =>
				DefinedValEx.withName(v.name + "Field")
					.withExpression(s"""$fieldClzName[${clz.name}]("${v.name}",_.${v.name})""".stripMargin)
		}
		val allFields = clz.vals.map(_.name + "Field")
		val methods = Seq(
			DefinedMethodEx.withName("allFields")
				.withReturnType(s"Seq[Field[${clz.name}]]")
				.withImplementation(s"Seq(${allFields.mkString(",")})")
		)

		ObjectEx.withName(clz.name + "Reflect")
			.withVals(fields)
			.withMethods(methods)
	}

}

object Reflect
{
	def forPackage(pckg: PackageEx, fieldClass: String) = new Builder(pckg, fieldClass, _ => true)

	class Builder(pckg: PackageEx, fieldClass: String, classFilter: ClassEx => Boolean)
	{
		def withClassFilter(cf: ClassEx => Boolean) = new Builder(pckg, fieldClass, cf)

		def build = new Reflect(pckg, classFilter, fieldClass).reflect
	}

}