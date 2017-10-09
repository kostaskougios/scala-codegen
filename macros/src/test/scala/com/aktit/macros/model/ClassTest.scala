package com.aktit.macros.model

import com.aktit.macros.AbstractSuite

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 06/10/17
  */
class ClassTest extends AbstractSuite
{
	test("withName") {
		val t = Class.parser(q"class x(i:Int) { def f: String = i.toString }")
		val tm = t.withName("y")
		tm.syntax should be(q"class y(i:Int) { def f: String = i.toString }".syntax)
	}

	test("withMethods") {
		val t = Class.parser(q"class x(i:Int) { def f: String = i.toString }")
		val tm = t.withMethods(t.methods.map(_.withName("ff")))
		tm.syntax should be(q"class x(i:Int) { def ff: String = i.toString }".syntax)
	}

	//	test("fields") {
	//		val t = Class.parser(q"class X(val i:Int) { val y:Int=5 ; def z:Int=6 }")
	//		println(t.paramss)
	//		println(t.template)
	//	}
}
