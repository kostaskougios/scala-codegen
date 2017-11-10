
name := "lang-enhance"

lazy val commonSettings = Seq(
	organization := "com.aktit",
	version := "0.1.1-SNAPSHOT",
	scalaVersion := "2.12.4",
	scalacOptions += "-feature",
	scalacOptions += "-unchecked",
	scalacOptions += "-deprecation"
)

lazy val plugin = project.settings(
	commonSettings,
	name := "lang-enhance-plugin",
	sbtPlugin := true,
	libraryDependencies += "org.scalameta" %% "scalameta" % "2.0.1",
	libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)
