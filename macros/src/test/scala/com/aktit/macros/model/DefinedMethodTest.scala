package com.aktit.macros.model

import com.aktit.macros.AbstractSuite

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class DefinedMethodTest extends AbstractSuite
{
	test("withName") {
		val method = DefinedMethod.parser(q"def f(i:Int): String = { i.toString }")
		method.withName("fMod").syntax should be(q"def fMod(i:Int): String = { i.toString }".syntax)
	}

	test("noArgReturningUnit") {
		DefinedMethod.noArgReturningUnit("aMethod").syntax should be(q"def aMethod: Unit = {}".syntax)
	}

	test("define using code") {
		DefinedMethod.parseString("def x:Int = 5").name should be("x")
	}

	test("change parameters, name") {
		val method = DefinedMethod.parseString("def f(i:Int):String= i.toString")
		val modified = method.withParameters(method.parameters.map(_.map(p => p.withName(p.name + "x"))))
		modified.syntax should be(q"def f(ix:Int):String = i.toString".syntax)
	}

	test("change parameters, type") {
		val method = DefinedMethod.parseString("def f(i:Int):String = i.toString")
		val modified = method.withParameters(method.parameters.map(_.map(p => p.withType("Seq[Int]"))))
		modified.syntax should be(q"def f(i:Seq[Int]):String=i.toString".syntax)
	}

	test("add parameter") {
		val method = DefinedMethod.parseString("def f(i:Int):String=i.toString")
		val modified = method.withParameters(method.parameters ++ Seq(Seq(Param.parseString("j:String"))))
		modified.syntax should be(q"def f(i:Int)(j:String):String=i.toString".syntax)
	}

	test("change return type") {
		val method = DefinedMethod.parseString("def f(i:Int):String=i.toString")
		val modified = method.withReturnType("Seq[Long]")
		modified.syntax should be(q"def f(i:Int):Seq[Long]=i.toString".syntax)
	}

	test("change method implementation") {
		val method = DefinedMethod.parseString("def f(i:Int):String=i.toString")
		val modified = method.withImplementation("(i+1).toString")
		modified.syntax should be(q"def f(i:Int):String=(i+1).toString".syntax)
	}
}