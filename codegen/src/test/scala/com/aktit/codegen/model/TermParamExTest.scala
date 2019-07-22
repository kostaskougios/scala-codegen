package com.aktit.codegen.model

import com.aktit.codegen.AbstractSuite

import scala.meta._

/**
  * @author kostas.kougios
  *         22/07/19 - 21:53
  */
class TermParamExTest extends AbstractSuite
{
	test("fromSource") {
		TermParamEx.fromSource("x: Int").syntax should be(q"x: Int".syntax)
	}

	test("withType") {
		TermParamEx.fromSource("x: Int").withType("Long").syntax should be(q"x: Long".syntax)
	}

	test("withName") {
		TermParamEx.fromSource("x: Int").withName("y").syntax should be(q"y: Int".syntax)
	}

	test("withMods") {
		TermParamEx.fromSource("x: Int")
			.toVal.syntax should be(q"val x: Int".syntax)
	}

}
