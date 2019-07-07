package com.aktit.codegen.patterns

import com.aktit.codegen.model.PackageEx
import org.scalatest.FunSuite

/**
  * @author kostas.kougios
  *         07/07/19 - 21:30
  */
class CombineCaseClassesTest extends FunSuite
{
	test("combines case classes") {
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
		val combined = CombineCaseClasses.combine("BasketedItem", itemPackage.classes ++ basketPackage.classes: _*)
		println(combined.syntax)
	}
}
