package com.aktit.macros.model

import com.aktit.macros.Parser

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
case class Package(meta: Package.Meta, children: Seq[Code]) extends Code
	with Meta.Contains[Package.Meta]
	with Code.Name[Package]
{
	def name: String = meta.nameTerm.syntax

	override def tree = meta.tree

	override def withName(name: String) = copy(meta = meta.copy(nameTerm = Term.Name(name)))

	def traits: Seq[Trait] = children.collect {
		case t: Trait => t
	}

	def classes: Seq[Class] = children.collect {
		case c: Class => c
	}

	def imports: Seq[Import] = children.collect {
		case i: Import => i
	}

	override def toString = tree.syntax

}

object Package extends PartialParser[Package]
{
	/**
		* Parses source and creates the Package (which includes imports, traits and classes)
		*
		* @param src the source code, this is code in a string
		* @return Package
		*/
	def fromSource(src: String) = Parser().parseSource(src).collectFirst {
		case p: Package => p
	}.get

	override def parser = {
		case tree@q"package $ref { ..$topstats }" =>
			Package(
				Meta(
					tree,
					ref),
				topstats.map(
					Import.parser.orElse(Trait.parser).orElse(Class.parser)
				)
			)
	}

	case class Meta(tree: Tree, nameTerm: Term.Ref) extends com.aktit.macros.model.Meta

}