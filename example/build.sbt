import com.aktit.codegen.Parser
import com.aktit.codegen.patterns.CombineCaseClasses

name := "examples"

organization := "com.aktit"

val generate = taskKey[Unit]("Generates classes using codegen")

generate := {
	val combineDir = "src/main/scala/com/aktit/example/combine"
	val parser = Parser()
	val userPackage = parser.file(s"$combineDir/User.scala")
	val purchasePackage = parser.file(s"$combineDir/Purchase.scala")
	val userPurchases = CombineCaseClasses.createClass("com.aktit.example.combine", "UserPurchases")
		.fromPackages(userPackage, purchasePackage)
		.fromClasses(userPackage.classes.head, purchasePackage.classes.head)
		.build

	println(userPurchases.syntax)
}