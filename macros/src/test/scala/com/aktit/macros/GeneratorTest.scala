package com.aktit.macros

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class GeneratorTest extends AbstractSuite
{
	test("method generation") {
		val x = q"def f(i:Int): String"
		println(x.syntax)
	}
}
