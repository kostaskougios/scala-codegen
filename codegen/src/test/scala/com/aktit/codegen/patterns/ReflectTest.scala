package com.aktit.codegen.patterns

import com.aktit.codegen.model.{DefinedMethodEx, DefinedValEx, PackageEx}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

import scala.meta._

/**
  * @author kostas.kougios
  *         24/07/19 - 22:47
  */
class ReflectTest extends FunSuite
{
	test("reflect name") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")
		Reflect.forPackage(pckg, "com.aktit.reflect.Field").build.objects.map(_.name) should be(Seq("ItemReflect"))
	}

	test("reflect case classes vals") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")

		val reflect = Reflect.forPackage(pckg, "com.aktit.reflect.Field").build
		val vals = reflect.objects.head.vals
		vals should contain(DefinedValEx.parser(q"val idField = Field(id, _.id)"))
		vals should contain(DefinedValEx.parser(q"val dateField = Field(date, _.date)"))
	}

	test("reflect private vals") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				class Item(id:Int) {
					private val date:Date
	 			}
			}
		""")

		val reflect = Reflect.forPackage(pckg, "com.aktit.reflect.Field").build
		val vals = reflect.objects.head.vals
		vals should contain(DefinedValEx.parser(q"val dateField = Field(date, _.date)"))
	}

	test("reflect allFields") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")

		val reflect = Reflect.forPackage(pckg, "com.aktit.reflect.Field").build
		println(reflect.syntax)
		reflect.objects.head.methods should contain(DefinedMethodEx.parser(q"def allFields:Seq[Field] = Seq(idField,dateField)"))
	}
}
