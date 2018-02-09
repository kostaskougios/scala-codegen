lazy val commonSettings = Seq(
	organization := "com.aktit",
	version := "0.1.1-SNAPSHOT",
	scalaVersion := "2.12.4",
	scalacOptions += "-feature",
	scalacOptions += "-unchecked",
	scalacOptions += "-deprecation"
)

lazy val codegen = project.settings(
	commonSettings,
	libraryDependencies ++= Seq(
		"org.scalameta" %% "scalameta" % "3.2.0",
		"org.scalatest" %% "scalatest" % "3.0.5" % "test",
		"commons-io" % "commons-io" % "2.4"
	)

)

lazy val plugin = project.settings(
    commonSettings,
    name := "lang-enhance-plugin",
	sbtPlugin := true,
	libraryDependencies += "commons-io" % "commons-io" % "2.4"
).dependsOn(codegen)
