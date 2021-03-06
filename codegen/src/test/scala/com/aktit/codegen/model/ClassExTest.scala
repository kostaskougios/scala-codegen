package com.aktit.codegen.model

import com.aktit.codegen.AbstractSuite

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 06/10/17
  */
class ClassExTest extends AbstractSuite
{
	test("withName") {
		val t = ClassEx.parser(q"class x(i:Int) { def f: String = i.toString }")
		val tm = t.withName("y")
		tm.syntax should be(q"class y(i:Int) { def f: String = i.toString }".syntax)
	}

	test("withMethods") {
		val t = ClassEx.parser(q"class x(i:Int) { def f: String = i.toString }")
		val tm = t.withMethods(t.methods.map(_.withName("ff")))
		tm.syntax should be(q"class x(i:Int) { def ff: String = i.toString }".syntax)
	}

	test("methods") {
		val t = ClassEx.parser(
			q"""
               class X(val i:Int) {
                def f: String = i.toString
				val a:String
                def g: String = i.toString
                }
            """)
		t.methods.map(_.name) should be(Seq("f", "g"))
	}

	test("vals") {
		val c = ClassEx.parser(
			q"""
           class X(val constrArg:Int){
               private val privVal:Int = 5
               protected val protVal:Int=8
               val pubVal:String="x"
               def ignore:Long=10
           }
     """)
		c.vals.map(_.name) should be(Seq("constrArg", "privVal", "protVal", "pubVal"))
	}

	test("isCaseClass positive") {
		ClassEx.parser(q"case class X(constrArg:Int)").isCaseClass should be(true)
	}

	test("isCaseClass negative") {
		ClassEx.parser(q"class X").isCaseClass should be(false)
	}

	test("constructor arg without modifiers") {
		val c = ClassEx.parser(q"class X(constrArg:Int)")
		c.vals should be(Nil)
	}

	test("constructor public arg") {
		val c = ClassEx.parser(q"class X(val publ:Int)")
		c.vals.map(_.name) should be(Seq("publ"))
		c.vals.head.isPublic should be(true)
	}

	test("constructor private arg") {
		val c = ClassEx.parser(q"class X(private val priv:Int)")
		c.vals.map(_.name) should be(Seq("priv"))
		c.vals.head.isPrivate should be(true)
	}

	test("constructor arg a private val") {
		val c = ClassEx.parser(q"class X(private val constrArg:Int)")
		c.vals.map(_.name) should be(Seq("constrArg"))
	}

	test("case class vals") {
		val c = ClassEx.parser(q"case class X(constrArg:Int)")
		c.vals.map(_.name) should be(Seq("constrArg"))
		c.vals.count(_.isPublic) should be(1)
	}

	test("case class private vals") {
		val c = ClassEx.parser(q"case class X(private constrArg:Int)")
		c.vals.map(_.name) should be(Seq("constrArg"))
		c.vals.count(_.isPrivate) should be(1)
	}
}
