package com.aktit.codegen.patterns

import com.aktit.codegen.model.{DefinedMethodEx, DefinedValEx, PackageEx}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

import scala.meta._

/**
  * @author kostas.kougios
  *         24/07/19 - 22:47
  */
class CaseClassReflectTest extends FunSuite
{
	test("reflect extends Reflect") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
				case class Pack(id:Int)
			}
		""")
		CaseClassReflect.forPackage(pckg).build.objects.flatMap(_.extending.map(_.name)) should be(Seq("Reflect"))
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
		CaseClassReflect.forPackage(pckg)
			.withReflectConfig(CaseClassReflectConfig(classFilter = _.name != "Item"))
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
		CaseClassReflect.forPackage(pckg).build.objects.map(_.name) should be(Seq("ItemReflect"))
	}

	test("reflect case classes vals") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")

		val reflect = CaseClassReflect.forPackage(pckg).build
		val vals = reflect.objects.head.vals
		vals should contain(DefinedValEx.parser(q"""val idField = Field[Item,Int]("id", _.id,classOf[Int])"""))
		vals should contain(DefinedValEx.parser(q"""val dateField = Field[Item,Date]("date", _.date,classOf[Date])"""))
	}

	test("reflect allFields") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")

		val reflect = CaseClassReflect.forPackage(pckg).build
		reflect.objects.head.vals should contain(DefinedValEx.parser(q"val allFields = Seq(idField,dateField)"))
	}

	test("reflect tuples/toMap") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")

		val reflect = CaseClassReflect.forPackage(pckg).build
		val methods = reflect.objects.head.methods
		methods should contain(DefinedMethodEx.parser(q"def tuples(c:Item) = allFields.map(f => (f.name, f.getter(c)))"))
		methods should contain(DefinedMethodEx.parser(q"def toMap(c:Item) = tuples(c).toMap"))
	}
}
