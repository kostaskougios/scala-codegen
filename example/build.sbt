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