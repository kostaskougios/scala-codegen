package com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
trait Method
{
	def name: String
}

object Method
{

	trait Contains
	{
		def template: Template

		def methods: immutable.Seq[Method] = template.children.collect {
			case tree@q"..$mods def $ename[..$tparams](...$paramss): $tpe" =>
				DeclaredMethod(tree, mods, ename, tparams, paramss, tpe)
			case tree@q"..$mods def $ename[..$tparams](...$paramss): $tpeopt = $expr" =>
				DefinedMethod(tree, mods, ename, tparams, paramss, tpeopt, expr)
		}

		def declaredMethods: immutable.Seq[DeclaredMethod] = methods.collect {
			case d: DeclaredMethod => d
		}

		def definedMethods: immutable.Seq[DefinedMethod] = methods.collect {
			case d: DefinedMethod => d
		}

	}

}
