package com.aktit.codegen.patterns

import com.aktit.codegen.model._
import org.apache.commons.lang3.StringUtils

/**
  * @author kostas.kougios
  *         24/07/19 - 22:24
  */
private class Reflect(
	pckg: PackageEx,
	reflectConfig: ReflectConfig
)
{

	import reflectConfig._

	private val fieldClzName = StringUtils.substringAfterLast(fieldClass, ".")

	def reflect = {
		val reflects = pckg.classes
			.filter(classFilter)
			.map(generateObject)
		PackageEx.withName(pckg.name)
			.withImports(pckg.imports :+ ImportEx.fromSource(s"import $fieldClass"))
			.withObjects(reflects)
	}

	private def generateObject(clz: ClassEx) = {
		val fields = clz.vals.map {
			v =>
				DefinedValEx.withName(v.name + "Field")
					.withExpression(s"""$fieldClzName[${clz.name}]("${v.name}",_.${v.name})""".stripMargin)
		}
		val allFields = clz.vals.map(_.name + "Field")
		val methods = Seq(
			DefinedMethodEx.withName("allFields")
				.withReturnType(s"Seq[Field[${clz.name}]]")
				.withImplementation(s"Seq(${allFields.mkString(",")})"),
			DefinedMethodEx.withName("toMap")
				.withParameter(s"c:${clz.name}")
				.withImplementation("allFields.map(f => (f.name, f.getter(c)))")
		)

		ObjectEx.withName(clz.name + "Reflect")
			.withVals(fields)
			.withMethods(methods)
	}

}

object Reflect
{
	def forPackage(pckg: PackageEx) = new Builder(pckg, ReflectConfig.Default)

	class Builder(pckg: PackageEx, reflectConfig: ReflectConfig)
	{
		def withReflectConfig(rc: ReflectConfig) = new Builder(pckg, rc)

		def build = new Reflect(pckg, reflectConfig).reflect
	}

}

case class ReflectConfig(
	fieldClass: String = "com.aktit.reflect.lib.Field",
	classFilter: ClassEx => Boolean = _ => true
)

object ReflectConfig
{
	val Default = ReflectConfig()
}