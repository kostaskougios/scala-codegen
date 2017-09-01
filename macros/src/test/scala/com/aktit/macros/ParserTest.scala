package com.aktit.macros

import com.aktit.macros.model.Package

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class ParserTest extends AbstractSuite
{
	test("package") {
		val p = codeParse("com.aktit.macros.ATrait").collectFirst {
			case p: Package => p
		}.get
		p.name should be("com.aktit.macros")
	}
}
