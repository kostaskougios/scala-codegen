package com.aktit.codegen.patterns

import com.aktit.codegen.model._

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

	def reflect = {
		val reflects = pckg.classes
			.filter(classFilter)
			.map(generateObject)
		PackageEx.withName(pckg.name)
			.withImports(pckg.imports :+ ImportEx.fromSource(s"import com.aktit.reflect.lib._"))
			.withObjects(reflects)
	}

	private def generateObject(clz: ClassEx) = {
		val allFields = clz.vals.map(_.name + "Field")
		val fields = clz.vals.map {
			v =>
				DefinedValEx.withName(v.name + "Field")
					.withExpression(s"""Field[${clz.name}]("${v.name}",_.${v.name})""".stripMargin)
		} ++ Seq(
			DefinedValEx.withName("allFields")
				.withExpression(s"Seq(${allFields.mkString(",")})")
		)
		val methods = Seq(
			DefinedMethodEx.withName("tuples")
				.withParameter(s"c:${clz.name}")
				.withImplementation("allFields.map(f => (f.name, f.getter(c)))"),
			DefinedMethodEx.withName("toMap")
				.withParameter(s"c:${clz.name}")
				.withImplementation("tuples(c).toMap")

		)

		ObjectEx.withName(clz.name + "Reflect")
			.withExtending(TypeEx("Reflect"))
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
	classFilter: ClassEx => Boolean = _ => true
)

object ReflectConfig
{
	val Default = ReflectConfig()
}