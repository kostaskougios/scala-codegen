package com.aktit.macros

import com.aktit.macros.model.PackageEx

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class ParserTest extends AbstractSuite
{
  test("package") {
    val p = PackageEx.fromSource(ATrait)
    p.name should be("com.aktit.macros")
  }

  test("trait") {
    val t = PackageEx.fromSource(ATrait).traits.head
    t.name should be("ATrait")
  }

  test("trait methods") {
    val t = PackageEx.fromSource(ATrait).traits.head
    t.methods.map(_.name) should be(Seq("method1", "method2", "methodWithImpl"))
  }

  test("trait declared methods") {
    val t = PackageEx.fromSource(ATrait).traits.head
    t.declaredMethods.map(_.name) should be(Seq("method1", "method2"))
  }

  test("trait defined methods") {
    val t = PackageEx.fromSource(ATrait).traits.head
    t.definedMethods.map(_.name) should be(Seq("methodWithImpl"))
  }

  val ATrait =
    """
      		  |package com.aktit.macros
      		  |
      		  |trait ATrait
      		  |{
      		  |	def method1: String
      		  |
      		  |	def method2(id: Int): String
      		  |
      		  |	def methodWithImpl(id: Int): String = "x"
      		  |}
      		  |
    		""".stripMargin
}
