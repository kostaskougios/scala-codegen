package com.aktit.macros

import com.aktit.macros.model.Package

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class ParserTest extends AbstractSuite
{
	test("package") {
		val p = packageOf("com.aktit.macros.ATrait")
		p.name should be("com.aktit.macros")
	}

	test("trait") {
		val p = packageOf("com.aktit.macros.ATrait")
		val t = p.traits.head
		t.name should be("ATrait")
	}

	private def packageOf(cn: String) = {
		codeParse(cn).collectFirst {
			case p: Package => p
		}.get
	}

}
