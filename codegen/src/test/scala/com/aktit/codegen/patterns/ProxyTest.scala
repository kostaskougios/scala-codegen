package com.aktit.codegen.patterns

import com.aktit.codegen.model.PackageEx
import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         23/06/19 - 23:56
  */
class ProxyTest
	extends FunSuite
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

	test("create a proxy class") {
		val proxy = Proxy.proxy(packageWithXClass)
		proxy.syntax should be(PackageEx.fromSource(
			"""
			  |package x
			  |import scala.concurrent.duration.{ Duration, FiniteDuration => FD }
			  |class XProxy[A](forwarder: (String, Seq[Any]) => Any) extends X[A] {
			  |  override def noArg = forwarder("noArg", Array())
			  |  override def noArgParams() = forwarder("noArgParams", Array())
			  |  override def oneArg(m: Int) = forwarder("oneArg", Array(m))
			  |  override def multiArgs(n: Int)(m: Int) = forwarder("multiArgs", Array(n, m))
			  |}
			  |
      """.stripMargin).syntax)
	}

}
