package com.aktit.codegen.model

import java.io.File

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

	test("saveUnder") {
		val srcDir = s"$newRandomTmpDir/src"
		PackageEx.withName("com.aktit").withClasses(Seq(ClassEx.withName("MyClass"))).saveUnder(srcDir)
		new File(srcDir + "/com/aktit/MyClass.scala").exists should be(true)
	}
}
