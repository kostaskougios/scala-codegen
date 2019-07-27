import com.aktit.codegen.patterns.{CombineCaseClasses, Reflect}

name := "examples"

organization := "com.aktit"

unmanagedSourceDirectories in Compile += baseDirectory.value / "src_generated"

val cgProject = com.aktit.codegen.Project("src_generated", "src/main/scala")

val generateCombined = taskKey[Unit]("Generates combined classes using codegen")

generateCombined := {
	val userPurchases = CombineCaseClasses.createClass("com.aktit.example.combine", "UserPurchases")
		.fromFirstClassOfEach(
			cgProject.toPackage("com.aktit.example.combine.User"),
			cgProject.toPackage("com.aktit.example.combine.Purchase")
		).withRemoveFields((clzEx, valEx) => valEx.name == "userId").build

	println(userPurchases.syntax)
	cgProject.save(userPurchases)
}

val generateReflect = taskKey[Unit]("Generates reflect classes using codegen")

generateReflect := {
	val fieldClass = "com.aktit.example.lib.Field"

	for {
		pckg <- Seq(
			cgProject.toPackage("com.aktit.example.reflect.Person")
		)
	} {
		cgProject.save(
			Reflect.forPackage(pckg, fieldClass).build
		)
	}

}