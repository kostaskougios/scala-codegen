import com.aktit.codegen.Parser
import com.aktit.codegen.patterns.CombineCaseClasses
import org.apache.commons.io.FileUtils


name := "examples"

organization := "com.aktit"

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
	FileUtils.writeStringToFile(new File(combineDir, "UserPurchases.scala"), userPurchases.syntax)
}