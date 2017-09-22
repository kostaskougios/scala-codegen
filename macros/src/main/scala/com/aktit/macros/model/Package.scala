package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
case class Package(tree: Tree, nameTerm: Term.Ref, children: Seq[N]) extends N
{
	def name: String = nameTerm.syntax

	def traits: Seq[Trait] = children.collect {
		case t: Trait => t
	}

	override def toString = tree.syntax
}

object Package extends PartialParser[Package]
{
	override def parser = {
		case tree@q"package $ref { ..$topstats }" =>
			Package(tree, ref, topstats.map(Trait.parser))
	}
}