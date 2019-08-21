## Example project

See [Spark example project using codegen-spark](../example-spark/)

## Sbt integration

Add a library dependency in `project/plugins.sbt` (find the latest version [here](https://search.maven.org/search?q=g:io.github.kostaskougios)):

```
libraryDependencies += "com.aktit" %% "codegen-spark" % VERSION
```

Then create the code generation sbt tasks in `build.sbt`:

```scala
import com.aktit.codegen.patterns._

unmanagedSourceDirectories in Compile += baseDirectory.value / "src_generated"

val cgProject = com.aktit.codegen.Project(
	"src_generated", // the folder where the generated code will be placed
	"src/main/scala" // a comma sep list of all project's src folders
)

``` 
## Use cases

### Combine the field of case classes (i.e. for combining spark tables)

This code generator can come handy in spark jobs where we join 2 or more tables and we want
to process/store the data in a type-safe way. Also we may want to exclude columns or even
include extra columns.

Assuming we have these 2 (or more) case classes:

```scala
import java.sql.Timestamp

case class User(id: Int, name: String)
case class Purchase(userId: Int, item: String, price: BigDecimal, time: Timestamp)
```

We can then auto-generate `UserPurchases` case class which contains all fields of User & Purchase 
apart from userId which is removed because it is a duplicate of id:

```scala
import java.sql.Timestamp

case class UserPurchases(id: Int, name: String, item: String, price: BigDecimal, time: Timestamp)

object UserPurchases
{
	def apply(user: User, purchase: Purchase): UserPurchases = UserPurchases(user.id, user.name, purchase.item, purchase.price, purchase.time)
}
``` 

This sbt task will do the generation:

```scala

import com.aktit.codegen.spark._

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

```

