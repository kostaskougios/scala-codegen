package com.aktit.codegen.patterns

import com.aktit.codegen.model.PackageEx
import org.scalatest.FunSuite

import scala.meta._

/**
  * @author kostas.kougios
  *         24/07/19 - 22:47
  */
class ReflectTest extends FunSuite
{
	test("reflect case classes") {
		val pckg = PackageEx.parser(
			q"""
			package x1 {
   				import java.sql.Date
				case class Item(id:Int,date:Date)
			}
		""")

		val reflect = Reflect.forPackage(pckg, "com.aktit.reflect.Field").build
		println(reflect.syntax)
	}
}
