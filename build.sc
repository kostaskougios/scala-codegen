import Deps._
import mill._
import mill.scalalib._
import mill.scalalib.publish.{Developer, License, PomSettings, VersionControl}
import mill.scalalib.scalafmt._

trait CommonPublished extends Common with PublishModule
{
	def publishVersion = "1.0.0-SNAPSHOT"
}

object codegen extends CommonPublished
{
	def pomSettings = commonPomSettings.copy(description = "generate scala classes")

	override def ivyDeps = Agg(
		Apache.CommonIO,
		Apache.CommonText,
		ScalaMeta
	)

	object test extends CommonTest
	{
		override def ivyDeps = Agg(ScalaTest)
	}
}

object `codegen-spark` extends CommonPublished
{
	def pomSettings = commonPomSettings.copy(description = "generate scala classes targeting spark projects")

	override def moduleDeps = Seq(codegen)

	override def ivyDeps = Agg(
		Apache.Spark.Sql
	)

	object test extends CommonTest
	{
		override def ivyDeps = Agg(ScalaTest)
	}

}

object reflectlib extends CommonPublished
{
	def pomSettings = commonPomSettings.copy(description = "generate scala classes")

	object test extends CommonTest
	{
		override def ivyDeps = Agg(ScalaTest)
	}

}

trait Common extends SbtModule with ScalafmtModule
{
	override def scalaVersion = "2.12.8"

	override def scalacOptions = Seq("-deprecation", "-feature", "-unchecked")

	def commonPomSettings = PomSettings(
		description = "",
		organization = "io.github.kostaskougios",
		url = "https://bitbucket.org/ariskk/lang-enhance/src/master/",
		licenses = Seq(License.MIT),
		versionControl = VersionControl.github("kostaskougios", "lang-enhance"),
		developers = Seq(
			Developer("kostaskougios", "Kostas Kougios", "https://github.com/kostaskougios")
		)
	)

	trait CommonTest extends Tests {
		override def testFrameworks = Seq("org.scalatest.tools.Framework")
	}
}

object Deps
{
	val ScalaMeta = ivy"org.scalameta::scalameta:4.1.9"

	val ScalaTest = ivy"org.scalatest::scalatest:3.0.8"

	object Apache
	{
		val CommonIO = ivy"commons-io:commons-io:2.4"

		val CommonText = ivy"org.apache.commons:commons-text:1.7"

		object Spark {
			val Version="2.4.4"
			val Sql = ivy"org.apache.spark::spark-sql:$Version"
		}
	}

}
