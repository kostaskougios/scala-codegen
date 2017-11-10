
name := "lang-enhance"

version := "0.1"

lazy val commonSettings = Seq(
	organization := "com.aktit",
	scalaVersion := "2.12.4",
	scalacOptions += "-feature",
	scalacOptions += "-unchecked",
	scalacOptions += "-deprecation"
)

lazy val plugin = project.settings(
	commonSettings,
	sbtPlugin := true,
	libraryDependencies += "org.scalameta" %% "scalameta" % "2.0.1",
	libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)
