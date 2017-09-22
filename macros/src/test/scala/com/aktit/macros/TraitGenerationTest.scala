package com.aktit.macros

import com.aktit.macros.model.Trait

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 22/09/17
  */
class TraitGenerationTest extends AbstractSuite
{
	test("withName") {
		val t = Trait.parser(q"def f(i:Int): String")
	}
}
