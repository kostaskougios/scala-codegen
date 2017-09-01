package com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
class Trait(
	tree: Tree,
	mods: Seq[Mod],
	tname: Type.Name,
	tparams: Seq[Type.Param],
	template: Template
) extends N
{
	def name: String = tname.value

	def methods: immutable.Seq[Method] = template.children.collect {
		case tree@q"..$mods def $ename[..$tparams](...$paramss): $tpe" =>
			new DeclaredMethod(tree, mods, ename, tparams, paramss, tpe)
		case tree@q"..$mods def $ename[..$tparams](...$paramss): $tpeopt = $expr" =>
			new DefinedMethod(tree, mods, ename, tparams, paramss, tpeopt, expr)
	}

	def declaredMethods: immutable.Seq[DeclaredMethod] = methods.collect {
		case d: DeclaredMethod => d
	}

	def definedMethods: immutable.Seq[DefinedMethod] = methods.collect {
		case d: DefinedMethod => d
	}


	override def toString = tree.syntax
}
