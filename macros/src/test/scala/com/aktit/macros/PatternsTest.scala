package com.aktit.macros

import com.aktit.macros.model.PackageEx

/**
  * @author kostas.kougios
  *         Date: 03/11/17
  */
class PatternsTest extends AbstractSuite
{
  val packageWithXClass = PackageEx.fromSource(
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

  test("create a decorator class") {
    val decorator = Patterns.decorator(packageWithXClass)
    decorator.syntax should be(PackageEx.fromSource(
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

  test("create a proxy class") {
    val proxy = Patterns.proxy(packageWithXClass)
    println(proxy.syntax)
  }
}
