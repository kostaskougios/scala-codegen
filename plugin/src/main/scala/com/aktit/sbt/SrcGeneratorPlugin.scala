package com.aktit.sbt

import sbt._

/**
  * @author kostas.kougios
  */
object SrcGeneratorPlugin extends AutoPlugin
{

	// by defining autoImport, the settings are automatically imported into user's `*.sbt`
	object autoImport
	{
		// configuration points, like the built-in `version`, `libraryDependencies`, or `compile`
	}

	override def requires = sbt.plugins.JvmPlugin

	// This plugin is automatically enabled for projects which are JvmPlugin.
	override def trigger = allRequirements

}
