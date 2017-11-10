package com.aktit.codegen.model

import scala.meta.Tree

/**
  * @author kostas.kougios
  *         Date: 08/09/17
  */
trait CodeEx
{
    def tree: Tree

    def syntax: String = tree.syntax

    override def toString = syntax
}

object CodeEx
{

    trait Name[+T]
    {
        def name: String

        def withName(name: String): T
    }

}