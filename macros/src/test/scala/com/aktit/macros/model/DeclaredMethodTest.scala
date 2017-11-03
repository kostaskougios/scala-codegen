package com.aktit.macros.model

import com.aktit.macros.AbstractSuite

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class DeclaredMethodTest extends AbstractSuite
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

	test("change parameters, name") {
		val method = DeclaredMethod.parseString("def f(i:Int):String")
		val modified = method.withParameters(method.parameters.map(_.map(p => p.withName(p.name + "x"))))
		modified.syntax should be(q"def f(ix:Int):String".syntax)
	}

	test("change parameters, type") {
		val method = DeclaredMethod.parseString("def f(i:Int):String")
		val modified = method.withParameters(method.parameters.map(_.map(p => p.withType("Seq[Int]"))))
		modified.syntax should be(q"def f(i:Seq[Int]):String".syntax)
	}

	test("add parameter") {
		val method = DeclaredMethod.parseString("def f(i:Int):String")
		val modified = method.withParameters(method.parameters ++ Seq(Seq(TermParam.parseString("j:String"))))
		modified.syntax should be(q"def f(i:Int)(j:String):String".syntax)
	}

	test("change return type") {
		val method = DeclaredMethod.parseString("def f(i:Int):String")
		val modified = method.withReturnType("Seq[Long]")
		modified.syntax should be(q"def f(i:Int):Seq[Long]".syntax)
	}
}
