package com.aktit.codegen.model

import com.aktit.codegen.AbstractSuite

import scala.meta._

class ImportExTest extends AbstractSuite
{
	test("import") {
		val imports = ImportEx.parser(q"import com.pcg1.A")
		imports.imports should be(Seq(TypeImport("com.pcg1", "A")))
	}

	test("multiple imports") {
		val imports = ImportEx.parser(q"import com.pcg1.{A,B}")
		imports.imports should be(Seq(TypeImport("com.pcg1", "A"), TypeImport("com.pcg1", "B")))
	}

	test("renamed import") {
		val imports = ImportEx.parser(q"import com.pcg1.{A => AX}")
		imports.imports should be(Seq(TypeImportRenamed("com.pcg1", "A", "AX")))
	}

	test("wildcard import") {
		val imports = ImportEx.parser(q"import com.pcg1._")
		imports.imports should be(Seq(WildcardImport("com.pcg1")))
	}

}
