package com.aktit.macros

import com.aktit.macros.model.Package

/**
  * @author kostas.kougios
  *         Date: 03/11/17
  */
class PatternsTest extends AbstractSuite
{
  test("x") {

    val p = Package.fromSource(
      """
        |package x
        |
        |class X[A](x:X[A])
      """.stripMargin)

    val params = p.classes.map {
      c => c.constructorParameters
    }
    println(params)
  }

  test("create a decorator class") {
    val p = Package.fromSource(
      """
        |package x
        |
        |import scala.concurrent.duration.{Duration,FiniteDuration => FD}
        |
        |trait T1[T] {
        | def noArg : T
        |}
        |
        |trait T2 {
        | def oneArg(m:Int) : Int
        |}
        |
        |class X[A](val i:Int) extends T1[Int] with T2 {
        | // ignore
        | private val v1:Float
        | // ignore
        | val v2:Long
        | // ignore
        | private def privateDef = i*4
        | protected def protectedDef = i*4
        |
        | def noArg = i*2
        | def noArgParams() = i*3
        | def oneArg(m:Int) = m * i
        | def multiArgs(n:Int)(m:Int) = n*m*i
        |}
      """.stripMargin)

    val decorator = Patterns.decorator(p)
    decorator.syntax should be(Package.fromSource(
      """
        |package x
        |import scala.concurrent.duration.{ Duration, FiniteDuration => FD }
        |class XDecorator[A](enclosed: X[A]) extends T1[Int] with T2 {
        |  def noArg = enclosed.noArg
        |  def noArgParams() = enclosed.noArgParams()
        |  def oneArg(m: Int) = enclosed.oneArg(m)
        |  def multiArgs(n: Int)(m: Int) = enclosed.multiArgs(n)(m)
        |}
      """.stripMargin).syntax)
  }
}
