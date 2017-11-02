package com.aktit.macros.usecases

import com.aktit.macros.AbstractSuite
import com.aktit.macros.model.{ Class, Package, Param }

/**
  * @author kostas.kougios
  *         Date: 02/11/17
  */
class DecoratorCreationTest extends AbstractSuite
{
  test("create a decorator class") {
    val p = Package.fromSource(
      """
        |package x
        |
        |import scala.concurrent.duration.{Duration,FiniteDuration => FD}
        |
        |class X(val i:Int) {
        | def noArg = i*2
        | def noArgParams() = i*3
        | def oneArg(m:Int) = m * i
        | def multiArgs(n:Int)(m:Int) = n*m*i
        |}
      """.stripMargin)

    val classes = p.classes.map {
      clz =>
        val methods = clz.methods.map {
          method =>
            val args = Param.toString(method.parameters)
            val impl = s"enclosed.${method.name} $args"
            method.withImplementation(impl)
        }

        Class.withName(clz.name + "Decorator")
          .withConstructorParameters(Seq(Param.parseString(s"enclosed : ${clz.name}")))
          .withMethods(methods)
    }

    val decorator = Package.withName(p.name)
      .withImports(p.imports)
      .withClasses(classes)

    println(decorator.syntax)

  }
}