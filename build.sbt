import Deps._

lazy val commonSettings = Seq(
	organization := "com.aktit",
	version := "0.1.1-SNAPSHOT",
	scalaVersion := "2.12.8",
	scalacOptions += "-feature",
	scalacOptions += "-unchecked",
	scalacOptions += "-deprecation"
)

lazy val codegen = project.settings(
	commonSettings,
	libraryDependencies ++= Seq(
		ScalaMeta,
		ScalaTest,
		Apache.CommonIO
	)

)

lazy val plugin = project.settings(
	commonSettings,
	name := "lang-enhance-plugin",
	sbtPlugin := true,
	libraryDependencies += Apache.CommonIO
).dependsOn(codegen)
