package com.aktit.codegen.model

import com.aktit.codegen.AbstractSuite

class PackageExTest extends AbstractSuite
{
	test("name") {
		val p = PackageEx.fromSource(
			"""
		package p
		""")
		p.name should be("p")
	}

}
