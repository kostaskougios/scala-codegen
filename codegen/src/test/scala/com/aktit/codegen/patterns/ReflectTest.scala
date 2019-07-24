package com.aktit.codegen.patterns

import com.aktit.codegen.model.{DefinedValEx, PackageEx}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

import scala.meta._

/**
  * @author kostas.kougios
  *         24/07/19 - 22:47
  */
class ReflectTest extends FunSuite
{
	test("reflect case classes vals") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")

		val reflect = Reflect.forPackage(pckg, "com.aktit.reflect.Field").build
		println(reflect.syntax)
		val vals = reflect.classes.head.vals
		vals should contain(DefinedValEx.parser(q"val idField = com.aktit.reflect.Field(id, _.id)"))
		vals should contain(DefinedValEx.parser(q"val dateField = com.aktit.reflect.Field(date, _.date)"))
	}
}
