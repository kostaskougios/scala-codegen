package com.aktit.codegen.patterns

import com.aktit.codegen.model._

class CombineCaseClasses private(targetPackage: String, newClassName: String, packages: Seq[PackageEx], classes: Seq[ClassEx])
{
	def combine: PackageEx = {
		val vals = classes.flatMap(_.vals)
		val imports = packages.flatMap(_.imports).distinct

		val caseClass = ClassEx.withName(newClassName)
			.withConstructorParameters(vals.map(_.toValTermParamEx.withMods(ModsEx.empty)))
			.withCaseClass

		val companion = ObjectEx.withName(newClassName)
			.withMethods(Seq(createConstructorFromParts))

		PackageEx.withName(targetPackage)
			.withImports(imports)
			.withClasses(Seq(caseClass))
			.withObjects(Seq(companion))
	}

	private def createConstructorFromParts = {
		val applyArgs = classes.map(c => TermParamEx.parseString(s"${c.unCapitalizedName}: ${c.name} "))
		val applyConstructorArgs = classes.flatMap {
			c =>
				c.vals.map {
					v =>
						s"${c.unCapitalizedName}.${v.name}"
				}
		}.mkString(", ")
		DefinedMethodEx.withName("apply")
			.withParameters(Seq(applyArgs))
			.withReturnType(newClassName)
			.withImplementation(s"$newClassName($applyConstructorArgs)")

	}

}

/**
  * @author kostas.kougios
  *         07/07/19 - 21:14
  */
object CombineCaseClasses
{
	def createClass(targetPackage: String, newClassName: String) = new Builder(targetPackage, newClassName)

	class Builder(targetPackage: String, newClassName: String)
	{
		def fromPackages(packages: PackageEx*) = new PackagesBuilder(packages)

		def fromFirstClassOfEach(packages: PackageEx*) = new PackagesBuilder(packages).fromClasses(packages.map(_.classes.head) : _*)

		class PackagesBuilder(packages: Seq[PackageEx])
		{
			def fromClasses(classes: ClassEx*) = new ClassesBuilder(classes)

			class ClassesBuilder(classes: Seq[ClassEx])
			{
				def build = new CombineCaseClasses(targetPackage, newClassName, packages, classes).combine
			}
		}

	}
}
