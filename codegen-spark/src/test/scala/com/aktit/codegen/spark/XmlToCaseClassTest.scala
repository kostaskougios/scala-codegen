package com.aktit.codegen.spark

import org.scalatest.FunSuite

/**
  * @author kostas.kougios
  *         02/09/2019 - 21:21
  */
class XmlToCaseClassTest extends FunSuite
{
	test("creates classes") {
		val pcg = XmlToCaseClass.createClasses("xml.test", "XmlClasses", "codegen-spark/test-files/xml/XmlToCaseClassTest1.xml")

		println(pcg.map(_.name).mkString("\n"))
	}

}
