package com.aktit.codegen.model

import com.aktit.codegen.AbstractSuite

import scala.meta._

class ImportExTest extends AbstractSuite
{
	test("import") {
		val imports = ImportEx.parser(q"import com.pcg1.A")
		imports.imports should be(Seq(Imported("com.pcg1", "A", None)))
	}

	test("multiple imports") {
		val imports = ImportEx.parser(q"import com.pcg1.{A,B}")
		imports.imports should be(Seq(Imported("com.pcg1", "A", None), Imported("com.pcg1", "B", None)))
	}

	test("renamed import") {
		val imports = ImportEx.parser(q"import com.pcg1.{A => AX}")
		imports.imports should be(Seq(Imported("com.pcg1", "A", Some("AX"))))
	}

}
