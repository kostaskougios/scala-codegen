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
	for (cf <- com.aktit.sbt.SbtService.withPackages(sourceDirectory.value.toString + "/main/scala", "some.service.impl")) {
		val decorator = com.aktit.codegen.Patterns.decorator(cf.pckg)
		println(decorator.syntax)
		com.aktit.sbt.SbtService.save(cf.copy(pckg = decorator), "/tmp/generated")
	}
}
