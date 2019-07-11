package com.aktit.codegen.patterns

import com.aktit.codegen.model.{ClassEx, PackageEx}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

import scala.meta._

/**
  * @author kostas.kougios
  *         07/07/19 - 21:30
  */
class CombineCaseClassesTest extends FunSuite
{
	test("combines case classes") {
		val itemPackage = PackageEx.parser(
			q"""
			package x1 {
				case class Item(id:Int,name:String)
			}
		""")

		val basketPackage = PackageEx.parser(
			q"""
			package x2 {
				case class Basket(discount:Float,numOfItems:Int)
			}
		""")

		val combined = CombineCaseClasses.combine("tx", "BasketedItem")(itemPackage, basketPackage)(itemPackage.classes ++ basketPackage.classes: _*)
		combined.classes.head should be(ClassEx.parser(q"case class BasketedItem(id: Int, name: String, discount: Float, numOfItems: Int)"))
	}

	test("import dependencies") {
		val itemWithDatePackage = PackageEx.parser(
			q"""
			package x1 {
				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")
		val basketWithDatePackage = PackageEx.parser(
			q"""
			package x2 {
				import java.sql.Date
				case class Basket(discount:Float,since:Date)
   			}
		""")

		val combined = CombineCaseClasses.combine("tx", "BasketedItem")(itemWithDatePackage, basketWithDatePackage)(itemWithDatePackage.classes ++ basketWithDatePackage.classes: _*)
		combined.classes.head should be(ClassEx.parser(
			q"case class BasketedItem(id: Int, date:Date, discount: Float, since: Date)"
		))
	}
}
