package com.aktit.codegen.model

import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         07/07/19 - 22:00
  */
class ModsExTest extends FunSuite
{
	test("empty") {
		ModsEx.empty.syntax should be("")
	}

	test("case class") {
		ModsEx.empty.withCase.syntax should be("case")
	}

	test("private") {
		ModsEx.empty.withPrivate.syntax should be("private")
	}

	test("protected") {
		ModsEx.empty.withProtected.syntax should be("protected")
	}

	test("val") {
		ModsEx.empty.withVal.syntax should be("val")
	}
}
