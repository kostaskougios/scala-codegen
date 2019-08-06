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
	test("reflect extends Reflect") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
				case class Pack(id:Int)
			}
		""")
		Reflect.forPackage(pckg).build.objects.flatMap(_.extending.map(_.name)) should be(Seq("Reflect"))
	}

	test("reflect filter out classes") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
				case class Pack(id:Int)
			}
		""")
		Reflect.forPackage(pckg)
			.withReflectConfig(ReflectConfig(classFilter = _.name != "Item"))
			.build
			.objects
			.map(_.name) should be(Seq("PackReflect"))
	}

	test("reflect name") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")
		Reflect.forPackage(pckg).build.objects.map(_.name) should be(Seq("ItemReflect"))
	}

	test("reflect case classes vals") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")

		val reflect = Reflect.forPackage(pckg).build
		val vals = reflect.objects.head.vals
		vals should contain(DefinedValEx.parser(q"""val idField = Field[Item]("id", _.id)"""))
		vals should contain(DefinedValEx.parser(q"""val dateField = Field[Item]("date", _.date)"""))
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

		val reflect = Reflect.forPackage(pckg).build
		val vals = reflect.objects.head.vals
		vals should contain(DefinedValEx.parser(q"""val dateField = Field[Item]("date", _.date)"""))
	}

	test("reflect allFields") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")

		val reflect = Reflect.forPackage(pckg).build
		reflect.objects.head.methods should contain(DefinedMethodEx.parser(q"def allFields:Seq[Field[Item]] = Seq(idField,dateField)"))
	}

	test("reflect toMap") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")

		val reflect = Reflect.forPackage(pckg).build
		reflect.objects.head.methods should contain(DefinedMethodEx.parser(q"def toMap(c:Item) = allFields.map(f => (f.name, f.getter(c)))"))
	}
}
