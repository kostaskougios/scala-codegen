import Deps._
import mill._
import mill.scalalib._

object codegen extends Common
{
	override def ivyDeps = Agg(
		Apache.CommonIO,
		Apache.CommonText,
		ScalaMeta
	)

	object test extends Tests
	{
		override def ivyDeps = Agg(ScalaTest)

		def testFrameworks = Seq("org.scalatest.tools.Framework")
	}

}

trait Common extends SbtModule
{
	override def scalaVersion = "2.12.8"

	override def scalacOptions = Seq("-deprecation", "-feature", "-unchecked")
}

object Deps
{
	val ScalaMeta = ivy"org.scalameta::scalameta:4.1.9"

	val ScalaTest = ivy"org.scalatest::scalatest:3.0.8"

	object Apache
	{
		val CommonIO = ivy"commons-io:commons-io:2.4"

		val CommonText = ivy"org.apache.commons:commons-text:1.7"
	}

}