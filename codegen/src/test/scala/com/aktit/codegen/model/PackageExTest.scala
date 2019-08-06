package com.aktit.codegen.model

import com.aktit.codegen.AbstractSuite

class PackageExTest extends AbstractSuite
{
	test("name") {
		val p = PackageEx.fromSource(
			"""
		package p.a
		""")
		p.name should be("p.a")
	}

	test("syntax") {
		val p = PackageEx.fromSource(
			"""
		package com.aktit.example.combine
		""")
		p.syntax should be("package com.aktit.example.combine")
	}

	test("withName") {
		PackageEx.withName("com.aktit").syntax should be("package com.aktit")
	}

	test("classes, when object present") {
		val p = PackageEx.fromSource(
			"""
		package test {
  			class X
	 		object XY
  		}
		""")
		p.classes.map(_.name) should be(Seq("X"))
	}
}
