package com.aktit.macros.usecases

import com.aktit.macros.AbstractSuite
import com.aktit.macros.model.Package

/**
  * @author kostas.kougios
  *         Date: 02/11/17
  */
class WrapperCreationTest extends AbstractSuite
{
  test("create a wrapper class") {
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
            val args = if (method.parameters.isEmpty) "" else method.parameters.map(_.map(_.name).mkString(",")).mkString("(", ")(", ")")
            val impl = s"enclosed.${method.name} $args"
            method.withImplementation(impl).syntax
        }
        s"""
           |class ${clz.name}Wrapper(enclosed : ${clz.name})
           |{
           |${methods.mkString("\n")}
           |}
         """.stripMargin
    }

    val wrapper =
      s"""
         |package ${p.name}
         |
       |${p.imports.mkString("\n")}
         |
       |${classes.mkString("\n")}
         """.stripMargin

    println(wrapper)

  }
}
