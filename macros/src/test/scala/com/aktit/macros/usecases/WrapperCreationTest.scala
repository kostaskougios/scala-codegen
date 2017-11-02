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
        | def y : Int =i*2
        |}
      """.stripMargin)

    val classes = p.classes.map {
      clz =>
        val methods = clz.methods.map {
          method =>
            val args = method.parameters.map(_.map(_.name).mkString(",")).mkString("(", ")(", ")")
            val impl = s"enclosed.${method.name}($args)"
            s"""
               |${method.toAbstract.withImplementation(impl)}
             """.stripMargin
        }
        s"""
           |class ${clz.name}Wrapper extends ${clz.name} {
           |{${methods.mkString("\n")}
           | }
         """.stripMargin
    }

    println(
      s"""
         |package ${p.name}
         |
       |${p.imports.mkString("\n")}
         |
       |${classes.mkString("\n")}
         """.stripMargin
    )

  }
}
