package com.aktit.macros

import com.aktit.macros.model.Package

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class ParserTest extends AbstractSuite
{
	test("package") {
		val f = codeFile("com.aktit.macros.ATrait")
		val p = Parser(f).parse.collectFirst {
			case p: Package => p
		}.get
		p.name should be("com.aktit.macros")

	}
}
