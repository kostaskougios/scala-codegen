package com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
trait Method extends Code
{
	def name: String
}

object Method
{

	trait Contains
	{
		def template: Template

		def methods: immutable.Seq[Method] = template.children
			.collect(DefinedMethod.parser.orElse(DeclaredMethod.parser))

		def declaredMethods: immutable.Seq[DeclaredMethod] = methods.collect {
			case d: DeclaredMethod => d
		}

		def definedMethods: immutable.Seq[DefinedMethod] = methods.collect {
			case d: DefinedMethod => d
		}

	}

}
