package com.aktit.codegen.patterns

import com.aktit.codegen.model.{ClassEx, PackageEx, TermParamEx}

/**
  * @author kostas.kougios
  *         04/04/19 - 21:43
  */
object Decorator
{
	def decorator(p: PackageEx): PackageEx = {
		val classes = p.classes.map {
			clz =>
				val methods = clz.methods.filter(_.isPublic).map {
					method =>
						val args = TermParamEx.toString(method.parameters)
						val impl = s"enclosed.${method.name} $args"
						method.withImplementation(impl)
				}

				ClassEx.withName(clz.name + "Decorator")
					.withTypeParams(clz.typeParams)
					.withConstructorParameter(clz.toTermParam("enclosed", clz.typeParams.map(_.toType)))
					.withMethods(methods)
					.withExtending(clz.extending)
		}

		val decorator = PackageEx.withName(p.name)
			.withImports(p.imports)
			.withClasses(classes)
		decorator
	}

}
