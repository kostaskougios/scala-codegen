# CodeGen
This scala library allows scala code generation via transforming other scala code
or by creating code from scratch.

A common use case is to load this in your build tool (i.e. sbt) and configure it
to generate new code via existing project code, i.e. to combine 2 or more case classes
into a generated case class that will contain all fields of the combined case classes.

The library is based on scala meta.

Why code generation and not macros? Because we can see the generated src code, if it doesn't
compile we can make sense of it, we can create code outside the compilation unit, we get full
support from ide's, it's as typesafe as macros because the code won't compile if it is incorrect,
we don't have to add any libary to our project's classpath (only to our build tool's classpath),
we can generate code not only from scala declarations but i.e. from a database. 
On the downside we have to add it as a build step to our build tool.

Please find the latest version [here](https://search.maven.org/search?q=g:io.github.kostaskougios)

## API

The library provides a number of classes that help parsing packages (which includes 
package, imports, classes, objects declarations), parsing classes, objects, defs, vals 
etc and utility code to actually read existing src files from projects.

```scala
val cgProject = com.aktit.codegen.Project(
	"src_generated", // the folder where the generated code will be placed
	"src/main/scala" // a comma sep list of all project's src folders
)

val pckg=cgProject.toPackage("com.aktit.example.combine.User")
pckg.classes // all classes of User.scala
pckg.objects // all objects

pckg.classes.flatMap(_.vals) // all vals of all classes in User.scala
... and so on
```

Code can also be generated from scratch using one of fromSource(), parser(), withName() or withX() 
methods:

```scala

PackageEx.withName("com.aktit").syntax should be("package com.aktit")

PackageEx.withName("com.aktit.code")
    .withClasses(Seq(
        ClassEx.withName("MyClass1")    
    ))
    

```

Code can then be converted to a string or straight saved into the disk:

```scala
PackageEx.withName("com.aktit").syntax // code as string

cgProject.save(PackageEx.withName("com.aktit")) // code saved to src_generated/com/aktit
```
[Create proxy of an existing class](codegen/src/main/scala/com/aktit/codegen/patterns/Proxy.scala)

[Create decorator of an existing class](codegen/src/main/scala/com/aktit/codegen/patterns/Decorator.scala)

[Example project](example/) 

## Sbt integration

Add a library dependency in `project/plugins.sbt` (find the latest version [here](https://search.maven.org/search?q=g:io.github.kostaskougios)):

```
libraryDependencies += "com.aktit" %% "codegen" % VERSION
```

Then create the code generation sbt tasks in `build.sbt`:

```scala
unmanagedSourceDirectories in Compile += baseDirectory.value / "src_generated"

val cgProject = com.aktit.codegen.Project(
	"src_generated", // the folder where the generated code will be placed
	"src/main/scala" // a comma sep list of all project's src folders
)

``` 

Now we are ready to create code-generation tasks for the configured codegen-project. See
the [Example project](example/) or use cases below.

## Use cases

### Spark

See [codegen-spark](codegen-spark/)

### Create reflection classes so that we can i.e. get a map of all field/values

Assuming we have a class like

```scala
case class Person(
	id: Int,
	name: String,
	dob: LocalDate
)
{
	def isBornAfter(p: Person) = dob.isAfter(p.dob)
}
```

We would like to avoid reflection and instead auto-generate a class like

```scala
object PersonReflect
{
	val idField = Field[Person]("id", _.id)
	val nameField = Field[Person]("name", _.name)
	val dobField = Field[Person]("dob", _.dob)

	def allFields: Seq[Field[Person]] = Seq(idField, nameField, dobField)

	def toMap(c: Person) = allFields.map(f => (f.name, f.getter(c)))
}
```

This way we can get i.e. a map of fields/values and serialize it to xml / json (assuming we
have to build a json library).

The sbt task:

```scala
import com.aktit.codegen.patterns._

val generateReflect = taskKey[Unit]("Generates reflect classes using codegen")

generateReflect := {
	for {
		pckg <- Seq(
			cgProject.toPackage("com.aktit.example.reflect.Person")
		)
	} {
		cgProject.save(
			CaseClassReflect.forPackage(pckg).build
		)
	}
}
```