import com.aktit.codegen.Parser
import com.aktit.codegen.patterns.{CombineCaseClasses, Reflect}

name := "examples"

organization := "com.aktit"

unmanagedSourceDirectories in Compile += baseDirectory.value / "src_generated"

val generateCombined = taskKey[Unit]("Generates combined classes using codegen")

generateCombined := {
	val srcDir = "src/main/scala/com/aktit/example/combine"
	val parser = Parser()
	val userPackage = parser.file(s"$srcDir/User.scala")
	val purchasePackage = parser.file(s"$srcDir/Purchase.scala")
	val userPurchases = CombineCaseClasses.createClass("com.aktit.example.combine", "UserPurchases")
		.fromFirstClassOfEach(userPackage, purchasePackage)
		.build

	println(userPurchases.syntax)
	userPurchases.saveUnder("src_generated")
}

val generateReflect = taskKey[Unit]("Generates reflect classes using codegen")

generateReflect := {
	val fieldClass = "com.aktit.example.lib.Field"
	val parser = Parser()
	val srcDir = "src/main/scala/com/aktit/example/reflect"

	val scalaFiles = Seq(s"$srcDir/Person.scala")

	for {
		scalaFile <- scalaFiles
	} {
		val pckg = parser.file(scalaFile)
		Reflect.forPackage(pckg, fieldClass).build.saveUnder("src_generated")
	}

}