package com.aktit.codegen.spark

import com.aktit.codegen.model._
import com.aktit.codegen.spark.CombineCaseClasses.{Beginning, End, ExtraField}

private class CombineCaseClasses private(
	targetPackage: String,
	newClassName: String,
	packages: Seq[PackageEx],
	classes: Seq[ClassEx],
	extraFields: Seq[ExtraField],
	removeFields: (ClassEx, ValEx) => Boolean
)
{
	private val beginning = extraFields.filter(_.pos == Beginning)
	private val end = extraFields.filter(_.pos == End)
	private val clzVals = classes.map(c => (c, c.vals.filter(v => !removeFields(c, v))))
	private val vals = clzVals.flatMap(_._2)

	def combine: PackageEx = {
		val imports = packages.flatMap(_.imports).distinct

		val caseClass = createCaseClass

		val companion = ObjectEx.withName(newClassName)
			.withMethods(Seq(createConstructorFromParts))

		PackageEx.withName(targetPackage)
			.withImports(imports)
			.withClasses(Seq(caseClass))
			.withObjects(Seq(companion))
	}

	private def createCaseClass = {

		ClassEx.withName(newClassName)
			.withConstructorParameters(
				beginning.map(_.param) ++
					vals.map(_.toValTermParamEx.withMods(ModsEx.empty)) ++
					end.map(_.param)
			)
			.withCaseClass
	}

	private def createConstructorFromParts = {
		val applyArgs = beginning.map(_.param) ++ classes.map(c => TermParamEx.fromSource(s"${c.unCapitalizedName}: ${c.name} ")) ++ end.map(_.param)
		val applyConstructorArgs = (
			beginning.map(_.param.name) ++
				clzVals.flatMap {
					case (c, cVals) =>
						cVals.map {
							v =>
								s"${c.unCapitalizedName}.${v.name}"
						}
				} ++
				end.map(_.param.name)
			)
		DefinedMethodEx.withName("apply")
			.withParameters(Seq(applyArgs))
			.withReturnType(newClassName)
			.withImplementation(s"$newClassName(${applyConstructorArgs.mkString(",")})")

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

		def fromFirstClassOfEach(packages: PackageEx*) = new PackagesBuilder(packages).fromClasses(packages.map(_.classes.head): _*)

		class PackagesBuilder(packages: Seq[PackageEx])
		{
			def fromClasses(classes: ClassEx*) = new ClassesBuilder(classes, Nil, (_, _) => false)

			class ClassesBuilder(classes: Seq[ClassEx], extraFields: Seq[ExtraField], removeFields: (ClassEx, ValEx) => Boolean)
			{
				def withExtraFields(extra: ExtraField*) = new ClassesBuilder(classes, extra, removeFields)

				def withRemoveFields(f: (ClassEx, ValEx) => Boolean) = new ClassesBuilder(classes, extraFields, f)

				def build = new CombineCaseClasses(targetPackage, newClassName, packages, classes, extraFields, removeFields).combine
			}

		}

	}

	sealed trait Pos

	object Beginning extends Pos

	object End extends Pos

	case class ExtraField(pos: Pos, param: TermParamEx)

	def extraField(pos: Pos, name: String, `type`: String) = ExtraField(pos, TermParamEx.fromSource(s"$name:${`type`}"))
}
