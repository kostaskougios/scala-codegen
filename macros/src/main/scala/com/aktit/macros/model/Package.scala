package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
case class Package(meta: Package.Meta, children: Seq[N]) extends N
	with Meta.Contains[Package.Meta]
	with Code
	with Code.Name[Package]
{
	def name: String = meta.nameTerm.syntax

	override def tree = meta.tree

	override def withName(name: String) = copy(meta = meta.copy(nameTerm = Term.Name(name)))

	def traits: Seq[Trait] = children.collect {
		case t: Trait => t
	}

	override def toString = tree.syntax

}

object Package extends PartialParser[Package]
{
	override def parser = {
		case tree@q"package $ref { ..$topstats }" =>
			Package(
				Meta(
					tree,
					ref),
				topstats.map(
					Trait.parser.orElse(Class.parser)
				)
			)
	}

	case class Meta(tree: Tree, nameTerm: Term.Ref) extends com.aktit.macros.model.Meta

}