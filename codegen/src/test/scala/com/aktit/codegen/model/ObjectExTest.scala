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
}
