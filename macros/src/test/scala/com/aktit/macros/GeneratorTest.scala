package com.aktit.macros

import com.aktit.macros.model.DeclaredMethod

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class GeneratorTest extends AbstractSuite
{
	test("method generation") {
		val method = DeclaredMethod.parser(q"def f(i:Int): String")
		method.withName("fMod").code should be(q"def fMod(i:Int): String".syntax)
	}

	test("empty method") {
		DeclaredMethod.declare("aMethod").code should be("def aMethod: Unit")
	}
}
