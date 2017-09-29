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

	test("change parameters") {
		val method = DeclaredMethod.parseString("def f(i:Int):String")
		val modified = method.withParameters(method.parameters.map(_.map(p => p.withName(p.name + "x"))))
		modified.syntax should be(q"def f(ix:Int):String".syntax)
	}
}
