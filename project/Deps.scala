import sbt._

/**
  * @author kostas.kougios
  *         03/04/19 - 20:43
  */
object Deps
{
	val ScalaMeta = "org.scalameta" %% "scalameta" % "4.1.0" withSources()

	val ScalaTest = "org.scalatest" %% "scalatest" % "3.0.5" % Test withSources()

	object Apache
	{
		val CommonIO = "commons-io" % "commons-io" % "2.4" withSources()
	}

}
