package com.aktit.codegen.model

import org.scalatest.FunSuite
import org.scalatest.Matchers._

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
class ValDeclaredExTest extends FunSuite
{
	val intVal = ValDeclaredEx.parser(q"val x:Int")

	test("name") {
		intVal.name should be("x")
	}

	test("withName") {
		intVal.withName("y").syntax should be(q"val y:Int".syntax)
	}

	test("type") {
		intVal.`type`.name should be("Int")
	}

	test("withType") {
		intVal.withType(TypeEx("Long")).syntax should be(q"val x:Long".syntax)
	}

	test("from source") {
		ValDeclaredEx.fromSource("val x:Int").syntax should be(q"val x:Int".syntax)
	}
}
