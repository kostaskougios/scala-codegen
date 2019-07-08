package com.aktit.codegen.patterns

import com.aktit.codegen.model.PackageEx
import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         07/07/19 - 21:30
  */
class CombineCaseClassesTest extends FunSuite
{
	private val itemPackage = PackageEx.fromSource(
		"""
		  |package x1
		  |
		  |case class Item(id:Int,name:String)
		""".stripMargin)

	private val basketPackage = PackageEx.fromSource(
		"""
		  |package x2
		  |
		  |case class Basket(discount:Float,numOfItems:Int)
		""".stripMargin)

	private val itemWithDatePackage = PackageEx.fromSource(
		"""
		  |package x1
		  |
		  |import java.sql.Date
		  |
		  |case class Item(id:Int,date:Date)
		""".stripMargin)
	private val basketWithDatePackage = PackageEx.fromSource(
		"""
		  |package x2
		  |
		  |import java.sql.Date
		  |
		  |case class Basket(discount:Float,since:Date)
		""".stripMargin)

	test("combines case classes") {
		val combined = CombineCaseClasses.combine("tx", "BasketedItem")(itemPackage, basketPackage)(itemPackage.classes ++ basketPackage.classes: _*)
		combined.syntax should be(PackageEx.fromSource(
			"""
			  |package tx
			  |
			  |case class BasketedItem(id: Int, name: String, discount: Float, numOfItems: Int)
			  |""".stripMargin
		).syntax
		)
	}

	test("import dependencies") {
		val combined = CombineCaseClasses.combine("tx", "BasketedItem")(itemWithDatePackage, basketWithDatePackage)(itemWithDatePackage.classes ++ basketWithDatePackage.classes: _*)
		combined.syntax should be(PackageEx.fromSource(
			"""
			  |package tx
			  |
			  |import java.sql.Date
			  |
			  |case class BasketedItem(id: Int, date:Date, discount: Float, since: Date)
			""".stripMargin).syntax
		)

	}
}
