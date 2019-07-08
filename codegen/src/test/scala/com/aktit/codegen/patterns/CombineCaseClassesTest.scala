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
	val itemPackage = PackageEx.fromSource(
		"""
		  |package x1
		  |
		  |case class Item(id:Int,name:String)
		""".stripMargin)

	val basketPackage = PackageEx.fromSource(
		"""
		  |package x2
		  |
		  |case class Basket(discount:Float,numOfItems:Int)
		""".stripMargin)

	test("combines case classes") {
		val combined = CombineCaseClasses.combine("tx", "BasketedItem", itemPackage.classes ++ basketPackage.classes: _*)
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
		val itemWithDate = PackageEx.fromSource(
			"""
			  |package x1
			  |
			  |import java.sql.Date
			  |
			  |case class Item(id:Int,date:Date)
			""".stripMargin)
		val combined = CombineCaseClasses.combine("tx", "BasketedItem", itemWithDate.classes ++ basketPackage.classes: _*)
		combined.syntax should be(PackageEx.fromSource(
			"""
			  |package tx
			  |
			  |import java.sql.Date
			  |
			  |case class BasketedItem(id: Int, date:Date, discount: Float, numOfItems: Int)
			""".stripMargin).syntax
		)

	}
}
