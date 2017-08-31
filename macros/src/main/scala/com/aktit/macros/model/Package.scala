package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/08/17
  */
class Package(tree: Tree, name: Term.Ref, children: Seq[N]) extends N
{
  def traits: Seq[Trait] = children.collect {
    case t: Trait => t
  }

	override def toString = tree.syntax
}