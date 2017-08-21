
name := "lang-enhance"

version := "0.1"

lazy val commonSettings = Seq(
  scalaVersion := "2.12.3",
  scalacOptions += "-feature",
  scalacOptions += "-unchecked",
  scalacOptions += "-deprecation"
)
lazy val macros = project.settings(
  commonSettings,
  libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0"
).dependsOn()

lazy val codeGen = TaskKey[Unit]("code-gen")

lazy val code = project.settings(
  commonSettings,
  codeGen := {
    val r = (runner in Compile).value
    val cp = (dependencyClasspath in Compile).value
    val s = streams.value
    r.run("com.aktit.macros.Runner", cp.files, Array(sourceDirectory.value.toString + "/main/scala", "my.code"), s.log) foreach sys.error
  }
).dependsOn(macros)