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
		val t = Class.parser(q"class x(i:Int) { def f: String = i*2 }")
		val tm = t.withName("y")
		tm.syntax should be(q"class y(i:Int) { def f: String = i*2 }".syntax)
	}

}
