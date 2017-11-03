package com.aktit.macros

import com.aktit.macros.model.{ Class, Package, Param }

/**
  * @author kostas.kougios
  *         Date: 03/11/17
  */
object Patterns
{
  def decorator(p: Package) = {
    val classes = p.classes.map {
      clz =>
        val methods = clz.methods.filter(_.isPublic).map {
          method =>
            val args = Param.toString(method.parameters)
            val impl = s"enclosed.${method.name} $args"
            method.withImplementation(impl)
        }

        Class.withName(clz.name + "Decorator")
          .withConstructorParameter(Param.parseString(s"enclosed : ${clz.name}"))
          .withMethods(methods)
          .withExtending(clz.extending)
    }

    val decorator = Package.withName(p.name)
      .withImports(p.imports)
      .withClasses(classes)
    decorator
  }
}
