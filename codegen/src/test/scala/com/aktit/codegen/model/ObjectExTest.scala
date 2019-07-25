package com.aktit.codegen.model

import org.scalatest.FunSuite
import org.scalatest.Matchers._

import scala.meta._

/**
  * @author kostas.kougios
  *         08/07/19 - 23:25
  */
class ObjectExTest extends FunSuite
{
	test("named object syntax") {
		ObjectEx.withName("AnObject").syntax should be(q"object AnObject {}".syntax)
	}

	test("named object") {
		ObjectEx.withName("AnObject").name should be("AnObject")
	}

	test("vals") {
		ObjectEx.parser(
			q"""
			   object Obj {
			   	val i=1
			   	val j=2
			   }
			 """).vals should be(Seq(ValEx.fromSource("val i=1"), ValEx.fromSource("val j=2")))
	}

	test("methods") {
		ObjectEx.parser(
			q"""
			   object Obj {
			   	def i=1
			   	def j=2
			   }
			 """).methods should be(Seq(MethodEx.fromSource("def i=1"), MethodEx.fromSource("def j=2")))
	}

}
