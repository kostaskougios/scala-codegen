package com.aktit.codegen.model

import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         07/07/19 - 22:00
  */
class ModsExTest extends FunSuite
{
	test("case class") {
		ModsEx.empty.withCase.syntax should be("case")
	}

	test("empty") {
		ModsEx.empty.syntax should be("")
	}
}
