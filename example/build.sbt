import com.aktit.codegen.patterns._

name := "examples"

organization := "io.github.kostaskougios"

unmanagedSourceDirectories in Compile += baseDirectory.value / "src_generated"

libraryDependencies ++= Seq(
	"io.github.kostaskougios" %% "reflectlib" % "1.0.0-SNAPSHOT"
)

val cgProject = com.aktit.codegen.Project(
	"src_generated", // the folder where the generated code will be placed
	"src/main/scala" // a comma sep list of all project's src folders
)

val generateCombined = taskKey[Unit]("Generates combined classes using codegen")

generateCombined := {
	val userPurchases = CombineCaseClasses.createClass("com.aktit.example.combine", "UserPurchases")
		.fromFirstClassOfEach( // will use the first class of each package below:
			cgProject.toPackage("com.aktit.example.combine.User"),
			cgProject.toPackage("com.aktit.example.combine.Purchase")
		).withRemoveFields((clzEx, valEx) => valEx.name == "userId") // remove userId because it is a duplicate of user.id
		.build

	println(userPurchases.syntax)
	cgProject.save(userPurchases) // save to the src_generated folder
}

val generateReflect = taskKey[Unit]("Generates reflect classes using codegen")

generateReflect := {
	val config = CaseClassReflectConfig.Default
	for {
		pckg <- Seq(
			cgProject.toPackage("com.aktit.example.reflect.Person")
		)
	} {
		cgProject.save(
			CaseClassReflect.forPackage(pckg).withReflectConfig(config).build
		)
	}
}