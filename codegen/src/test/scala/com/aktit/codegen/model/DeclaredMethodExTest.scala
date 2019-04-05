package com.aktit.codegen.model

import com.aktit.codegen.AbstractSuite

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class DeclaredMethodExTest extends AbstractSuite
{
	test("withName") {
		val method = DeclaredMethodEx.parser(q"def f(i:Int): String")
		method.withName("fMod").syntax should be(q"def fMod(i:Int): String".syntax)
	}

	test("noArgReturningUnit") {
		DeclaredMethodEx.noArgReturningUnit("aMethod").syntax should be("def aMethod: Unit")
	}

	test("define using code") {
		DeclaredMethodEx.parseString("def x:Int").name should be("x")
	}

	test("change parameters, name") {
		val method = DeclaredMethodEx.parseString("def f(i:Int):String")
		val modified = method.withParameters(method.parameters.map(_.map(p => p.withName(p.name + "x"))))
		modified.syntax should be(q"def f(ix:Int):String".syntax)
	}

	test("change parameters, type") {
		val method = DeclaredMethodEx.parseString("def f(i:Int):String")
		val modified = method.withParameters(method.parameters.map(_.map(p => p.withType("Seq[Int]"))))
		modified.syntax should be(q"def f(i:Seq[Int]):String".syntax)
	}

	test("add parameter") {
		val method = DeclaredMethodEx.parseString("def f(i:Int):String")
		val modified = method.withParameters(method.parameters ++ Seq(Seq(TermParamEx.parseString("j:String"))))
		modified.syntax should be(q"def f(i:Int)(j:String):String".syntax)
	}

	test("change return type") {
		val method = DeclaredMethodEx.parseString("def f(i:Int):String")
		val modified = method.withReturnType("Seq[Long]")
		modified.syntax should be(q"def f(i:Int):Seq[Long]".syntax)
	}

	test("returnType") {
		val method = DeclaredMethodEx.parseString("def f(i:Int):MyClass")
		method.returnType.map(_.name) should be(Some("MyClass"))
	}

}
