This scala library allows scala code generation via other scala code.
A common use case is to load this in your build tool (i.e. sbt) and configure it
to generate new code via existing project code, i.e. to combine 2 or more case classes
into a generated case class that will contain all fields of the combined case classes.

[Example build.sbt](example/build.sbt) 