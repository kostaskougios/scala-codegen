package com.aktit.codegen.patterns

import com.aktit.codegen.model._
import org.apache.commons.text.WordUtils

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
			.withConstructorParameters(vals.map(_.toValTermParamEx.withMods(ModsEx.empty)))
			.withCaseClass

		val applyArgs = classes.map(c => TermParamEx.parseString(s"${WordUtils.uncapitalize(c.name)}: ${c.name} "))
		val fromParts = DefinedMethodEx.withName("apply")
			.withParameters(Seq(applyArgs))
			.withReturnType(newClassName)

		val companion = ObjectEx.withName(newClassName)
			.withMethods(Seq(fromParts))

		PackageEx.withName(targetPackage)
			.withImports(imports)
			.withClasses(Seq(caseClass))
			.withObjects(Seq(companion))
	}
}
