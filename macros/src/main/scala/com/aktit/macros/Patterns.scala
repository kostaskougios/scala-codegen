package com.aktit.macros

import com.aktit.macros.model.{ ClassEx, PackageEx, TermParamEx }

/**
  * @author kostas.kougios
  *         Date: 03/11/17
  */
object Patterns
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

    def proxy(p: PackageEx): PackageEx = {
        val classes = p.classes.map {
            clz =>
                val methods = clz.methods.filter(_.isPublic).map {
                    method =>
                        val impl = s"""forwarder("${method.name}",Seq(${method.parameters.flatMap(_.map(_.name))}))"""
                        method.withImplementation(impl)
                }

                ClassEx.withName(clz.name + "Proxy")
                    .withTypeParams(clz.typeParams)
                    .withConstructorParameter(TermParamEx.parseString("forwarder:(String,Seq[Any])=>Any"))
                    .withMethods(methods)
                    .withExtending(clz.toType)
        }
        val proxy = PackageEx.withName(p.name)
            .withImports(p.imports)
            .withClasses(classes)
        proxy
    }
}
