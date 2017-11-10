package com.aktit.macros.model

import com.aktit.macros.AbstractSuite

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

    test("vals constructor negative") {
        val c = ClassEx.parser(
            q"""
           class X(constrArg:Int){
           }
     """)
        c.vals should be(Nil)
    }
}
