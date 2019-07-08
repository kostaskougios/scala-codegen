package com.aktit.codegen.patterns

import com.aktit.codegen.model.{ClassEx, ModsEx, PackageEx}

/**
  * @author kostas.kougios
  *         07/07/19 - 21:14
  */
object CombineCaseClasses
{
	def combine(targetPackage: String, newClassName: String)(packages: PackageEx*)(classes: ClassEx*) = {
		val vals = classes.flatMap(_.vals)
		val imports = packages.flatMap(_.imports).distinct

		val caseClass = ClassEx.withName(newClassName)
			.withConstructorParameters(vals.map(_.toTermParamEx.withMods(ModsEx.empty)))
			.withCaseClass

		PackageEx.withName(targetPackage)
			.withImports(imports)
			.withClasses(Seq(caseClass))
	}
}
