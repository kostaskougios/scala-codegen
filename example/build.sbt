


name := "examples"

organization := "com.aktit"

unmanagedSourceDirectories in Compile += baseDirectory.value / "src_generated"

val generate = taskKey[Unit]("Generates classes using codegen")

generate := {
	val combineDir = "src/main/scala/com/aktit/example/combine"
	val parser = Parser()
	val userPackage = parser.file(s"$combineDir/User.scala")
	val purchasePackage = parser.file(s"$combineDir/Purchase.scala")
	val userPurchases = CombineCaseClasses.createClass("com.aktit.example.combine", "UserPurchases")
		.fromFirstClassOfEach(userPackage, purchasePackage)
		.build

	println(userPurchases.syntax)
	userPurchases.saveUnder("src_generated")
}