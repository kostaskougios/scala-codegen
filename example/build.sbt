

name := "example"

version := "0.1"

organization := "com.aktit"

scalaVersion := "2.12.4"

scalacOptions += "-feature"

lazy val codeGen = TaskKey[Unit]("code-gen")

codeGen := {
    val r = (runner in Compile).value
    val cp = (dependencyClasspath in Compile).value
    val s = streams.value
    SbtService.withPackages(sourceDirectory.value.toString + "/main/scala", "some.model")
}
