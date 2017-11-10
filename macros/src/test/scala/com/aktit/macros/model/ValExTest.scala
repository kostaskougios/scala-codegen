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
    test("name") {
        ValEx.parser(q"val x:Int").name should be("x")
    }

    test("withName") {
        ValEx.parser(q"val x:Int").withName("y").syntax should be(q"val y:Int".syntax)
    }
}
