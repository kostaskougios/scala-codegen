package com.aktit.codegen.patterns

import com.aktit.codegen.model.{ClassEx, DefinedMethodEx, PackageEx}

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
			.withClasses(reflects)
	}

	private def generateReflect(clz: ClassEx) = {
		val valAccessors = clz.vals.map {
			v =>
				DefinedMethodEx.withName(v.name + "Field")
					.withImplementation(s"$fieldClass(${v.name},_.${v.name})")
		}
		ClassEx.withName(clz.name + "Reflect")
			.withMethods(valAccessors)
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