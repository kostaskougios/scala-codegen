import com.aktit.codegen.spark._

scalaVersion := "2.11.8"

name := "example-spark"

organization := "io.github.kostaskougios"

unmanagedSourceDirectories in Compile += baseDirectory.value / "src_generated"

val SparkVersion = "2.4.3"

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-core" % SparkVersion,
	"org.apache.spark" %% "spark-sql" % SparkVersion,
	"com.databricks" %% "spark-xml" % "0.6.0"
)

val cgProject = com.aktit.codegen.Project(
	"src_generated", // the folder where the generated code will be placed
	"src/main/scala" // a comma sep list of all project's src folders
)

val generateCombined = taskKey[Unit]("Generates combined case classes using codegen")

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

val generateCsv = taskKey[Unit]("Generates csv case classes from sample csv files")

generateCsv := {
	val pcg = CsvToCaseClass.createClass("com.aktit.example.csv", "MyCsv", "csv-files/my.csv")

	println(pcg.syntax)
	cgProject.save(pcg) // save to the src_generated folder
}
