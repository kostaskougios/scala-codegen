package com.aktit.codegen.patterns

import com.aktit.codegen.model.{ClassEx, ModsEx, ObjectEx, PackageEx}

/**
  * @author kostas.kougios
  *         07/07/19 - 21:14
  */
object CombineCaseClasses
{
	def combine(targetPackage: String, newClassName: String)(packages: PackageEx*)(classes: ClassEx*): PackageEx = {
		val vals = classes.flatMap(_.vals)
		val imports = packages.flatMap(_.imports).distinct

		val caseClass = ClassEx.withName(newClassName)
			.withConstructorParameters(vals.map(_.toTermParamEx.withMods(ModsEx.empty)))
			.withCaseClass

		val companion = ObjectEx.withName(newClassName)

		PackageEx.withName(targetPackage)
			.withImports(imports)
			.withClasses(Seq(caseClass))
			.withObjects(Seq(companion))
	}
}
