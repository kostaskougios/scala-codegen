package com.aktit.codegen.model

import org.scalatest.FunSuite
import org.scalatest.Matchers._

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
class ValDefinedExTest extends FunSuite
{
	val intVal = ValDefinedEx.parser(q"val x:Int = 5")

	test("name") {
		intVal.name should be("x")
	}

	test("withName") {
		intVal.withName("y").syntax should be(q"val y:Int = 5".syntax)
	}

	test("type") {
		intVal.`type`.name should be("Int")
	}

	test("withType") {
		intVal.withType(TypeEx("Long")).syntax should be(q"val x:Long = 5".syntax)
	}

	test("from source") {
		ValDefinedEx.fromSource("val x:Int=5").syntax should be(q"val x:Int=5".syntax)
	}

}
