package com.aktit.codegen.model

import com.aktit.codegen.Parser

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
case class PackageEx(
    meta: PackageEx.Meta,
    children: Seq[CodeEx]
) extends CodeEx
    with MetaEx.Contains
    with CodeEx.Name[PackageEx]
{
    def name: String = meta.nameTerm.syntax

    override def tree = {
        val topstats = children.map(_.tree.asInstanceOf[Stat]).toList
        q"package ${meta.nameTerm} { ..$topstats }"
    }

    override def withName(name: String) = copy(meta = meta.copy(nameTerm = Term.Name(name)))

    def withTraits(traits: Seq[ClassEx]) = copy(
        children = children ++ traits
    )

    def traits: Seq[TraitEx] = children.collect {
        case t: TraitEx => t
    }

    def withClasses(classes: Seq[ClassEx]) = copy(
        children = children ++ classes
    )

    def classes: Seq[ClassEx] = children.collect {
        case c: ClassEx => c
    }

    def withImports(imports: Seq[ImportEx]) = copy(
        children = imports ++ children
    )

    def imports: Seq[ImportEx] = children.collect {
        case i: ImportEx => i
    }

    override def toString = tree.syntax

}

object PackageEx extends PartialParser[PackageEx]
{
    /**
      * Parses source and creates the Package (which includes imports, traits and classes)
      *
      * @param src the source code, this is code in a string
      * @return Package
      */
    def fromSource(src: String) = Parser().parseSource(src).collectFirst {
        case p: PackageEx => p
    }.get

    case class Meta(tree: Tree, nameTerm: Term.Ref) extends com.aktit.codegen.model.MetaEx

    override def parser = {
        case tree@q"package $ref { ..$topstats }" =>
            PackageEx(
                Meta(
                    tree,
                    ref),
                topstats.map(
                    ImportEx.parser.orElse(TraitEx.parser).orElse(ClassEx.parser)
                )
            )
    }

    def withName(name: String): PackageEx = parser(q"package x {}").withName(name)
}