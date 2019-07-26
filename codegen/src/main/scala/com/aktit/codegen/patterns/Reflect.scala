package com.aktit.codegen.patterns

import com.aktit.codegen.model._

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
	def reflect = {
		val reflects = pckg.classes
			.filter(classFilter)
			.map(generateReflect)
		PackageEx.withName(pckg.name)
			.withImports(pckg.imports)
			.withObjects(reflects)
	}

	private def generateReflect(clz: ClassEx) = {
		val fields = clz.vals.map {
			v =>
				DefinedValEx.withName(v.name + "Field")
					.withExpression(s"$fieldClass(${v.name},_.${v.name})")
		}
		val allFields = clz.vals.map(_.name + "Field")
		val methods = Seq(
			DefinedMethodEx.withName("allFields")
				.withReturnType("Seq[Field]")
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