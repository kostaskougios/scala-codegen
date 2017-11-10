package com.aktit.codegen.model

import com.aktit.codegen.AbstractSuite

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 22/09/17
  */
class TraitExTest extends AbstractSuite
{
    test("withName") {
        val t = TraitEx.parser(q"trait x { def f(i:Int): String }")
        val tm = t.withName("y")
        tm.syntax should be(q"trait y { def f(i:Int): String }".syntax)
    }

    test("withMethods") {
        val t = TraitEx.parser(q"trait x { def f(i:Int): String }")
        val tm = t.withMethods(t.methods.map(_.withName("ff")))
        tm.syntax should be(q"trait x { def ff(i:Int): String }".syntax)
    }

    test("copy a method") {
        val t = TraitEx.parser(q"trait x { def f(i:Int): String }")
        val toCopy = t.methods.head
        val tm = t.withMethods(t.methods :+ toCopy)
        tm.syntax should be(
            q"""
			   trait x {
			   	def f(i:Int): String
			   	def f(i:Int): String
			   	}
				""".syntax)
    }
}
