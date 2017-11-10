package com.aktit.macros.model

import org.scalatest.FunSuite
import org.scalatest.Matchers._

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
class ValExTest extends FunSuite
{
    val intVal = ValEx.parser(q"val x:Int")

    test("name") {
        intVal.name should be("x")
    }

    test("withName") {
        intVal.withName("y").syntax should be(q"val y:Int".syntax)
    }

    test("type") {
        intVal.`type`.name should be("Int")
    }

    test("withType") {
        intVal.withType(TypeEx("Long")).syntax should be(q"val x:Long".syntax)
    }
}
