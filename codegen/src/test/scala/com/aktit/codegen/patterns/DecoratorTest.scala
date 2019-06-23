package com.aktit.codegen.patterns

import com.aktit.codegen.model.PackageEx
import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         23/06/19 - 23:54
  */
class DecoratorTest
	extends FunSuite
{
	val packageWithSimpleClass = PackageEx.fromSource(
		"""
		  |package x
		  |
		  |import scala.concurrent.duration.{Duration,FiniteDuration => FD}
		  |
		  |trait T1 {
		  | def noArg : T
		  |}
		  |
		  |class X(i:Int) extends T1 {
		  | def noArg = i*2
		  |}
		""".stripMargin)

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
		  | private val v1:Float
		  | val v2:Long
		  | private def privateDef = i*4
		  | protected def protectedDef = i*4
		  |
		  | def noArg = i*2
		  | def noArgParams() = i*3
		  | def oneArg(m:Int) = m * i
		  | def multiArgs(n:Int)(m:Int) = n*m*i
		  |}
		""".stripMargin)

	test("create decorator simple") {
		val decorator = Decorator.decorator(packageWithSimpleClass)
		decorator.syntax should be(PackageEx.fromSource(
			"""
			  |package x
			  |import scala.concurrent.duration.{ Duration, FiniteDuration => FD }
			  |class XDecorator(enclosed: X) extends T1 {
			  |  def noArg = enclosed.noArg
			  |}
			""".stripMargin).syntax)

	}

	test("create a decorator class") {
		val decorator = Decorator.decorator(packageWithXClass)
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

}
