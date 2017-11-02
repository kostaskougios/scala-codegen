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
        | def y=i*2
        |}
      """.stripMargin)

    println(p.children)
  }
}
