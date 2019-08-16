package com.aktit.codegen.spark

import com.aktit.codegen.model.PackageEx
import org.scalatest.FunSuite
import org.scalatest.Matchers._

import scala.meta._

/**
  * @author kostas.kougios
  *         13/08/2019 - 08:29
  */
class CsvToCaseClassTest extends FunSuite
{
	test("creates case class from doubly quoted csv") {
		val pcg = CsvToCaseClass.createClass("csv.test", "MyCsv", "codegen-spark/test-files/csv/CsvToCaseClassTest1.csv")
		pcg should be(PackageEx.parser(
			q"""
				package csv.test {
					class MyCsv(id: String, header1: String, aDate: String, someSpecialChars: String)
	 			}
			 """))
	}

	test("creates case class from unquoted csv") {
		val pcg = CsvToCaseClass.createClass("csv.test", "MyCsv", "codegen-spark/test-files/csv/CsvToCaseClassTest2.csv")
		pcg should be(PackageEx.parser(
			q"""
				package csv.test {
					class MyCsv(id: String, header1: String, aDate: String, someSpecialChars: String)
	 			}
			 """))
	}
}
