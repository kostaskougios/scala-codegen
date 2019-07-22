package com.aktit.codegen.patterns

import com.aktit.codegen.model.{ClassEx, PackageEx, TermParamEx}

/**
  * @author kostas.kougios
  *         04/04/19 - 21:46
  */
object Proxy
{
	def proxy(p: PackageEx): PackageEx = {
		val classes = p.classes.map {
			clz =>
				val methods = clz.methods.filter(_.isPublic).map {
					method =>
						val impl = s"""forwarder("${method.name}",Array(${method.parameters.flatMap(_.map(_.name)).mkString(",")}))"""
						method.withImplementation(impl).withOverrides
				}

				ClassEx.withName(clz.name + "Proxy")
					.withTypeParams(clz.typeParams)
					.withConstructorParameter(TermParamEx.fromSource("forwarder:(String,Seq[Any])=>Any"))
					.withMethods(methods)
					.withExtending(clz.toType)
		}
		val proxy = PackageEx.withName(p.name)
			.withImports(p.imports)
			.withClasses(classes)
		proxy
	}
}
