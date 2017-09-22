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
		val t = Trait.parser(q"trait x { def f(i:Int): String }")
		val tm = t.withMethods(t.methods.map(_.withName("ff")))
		tm.tree.syntax should be(q"trait x { def ff(i:Int): String }".syntax)
	}
}
