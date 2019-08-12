package com.aktit.codegen.patterns

import com.aktit.codegen.model._

/**
  * @author kostas.kougios
  *         24/07/19 - 22:24
  */
private class CaseClassReflect(
	pckg: PackageEx,
	reflectConfig: CaseClassReflectConfig
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
		val vals = clz.constructorVals
		val allFields = vals.map(_.name + "Field")
		val fields = vals.map {
			v =>
				DefinedValEx.withName(v.name + "Field")
					.withExpression(s"""Field[${clz.name},${v.`type`.syntax}]("${v.name}",_.${v.name},classOf[${v.`type`.name}])""".stripMargin)
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
			.withExtending(TypeEx.fromSource(s"Reflect[${clz.name}]"))
			.withVals(fields)
			.withMethods(methods)
	}

}

object CaseClassReflect
{
	def forPackage(pckg: PackageEx) = new Builder(pckg, CaseClassReflectConfig.Default)

	class Builder(pckg: PackageEx, reflectConfig: CaseClassReflectConfig)
	{
		def withReflectConfig(rc: CaseClassReflectConfig) = new Builder(pckg, rc)

		def build = new CaseClassReflect(pckg, reflectConfig).reflect
	}

}

case class CaseClassReflectConfig(
	classFilter: ClassEx => Boolean = _ => true,
	stdLibReflect: Map[String, String] = CaseClassReflectConfig.StdLibReflectClasses
)

object CaseClassReflectConfig
{
	val StdLibReflectClasses = Map(
		"Int" -> "com.aktit.reflect.lib.scala.IntReflect",
		"String" -> "com.aktit.reflect.lib.java.StringReflect",
	)
	val Default = CaseClassReflectConfig()
}