package com.aktit.macros

import com.aktit.macros.model.DeclaredMethod

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class MethodGenerationTest extends AbstractSuite
{
	test("withName") {
		val method = DeclaredMethod.parser(q"def f(i:Int): String")
		method.withName("fMod").syntax should be(q"def fMod(i:Int): String".syntax)
	}

	test("noArgReturningUnit") {
		DeclaredMethod.noArgReturningUnit("aMethod").syntax should be("def aMethod: Unit")
	}

	test("define using code") {
		DeclaredMethod.parseString("def x:Int").name should be("x")
	}
}
