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
		val t = packageOf("com.aktit.macros.ATrait").traits.head
		t.name should be("ATrait")
	}

	test("trait methods") {
		val t = packageOf("com.aktit.macros.ATrait").traits.head
		t.methods.map(_.name) should be(Seq("method1", "method2", "methodWithImpl"))
	}

	test("trait declared methods") {
		val t = packageOf("com.aktit.macros.ATrait").traits.head
		t.declaredMethods.map(_.name) should be(Seq("method1", "method2"))
	}

	test("trait defined methods") {
		val t = packageOf("com.aktit.macros.ATrait").traits.head
		t.definedMethods.map(_.name) should be(Seq("methodWithImpl"))
	}

	private def packageOf(cn: String) = {
		codeParse(cn).collectFirst {
			case p: Package => p
		}.get
	}

}
