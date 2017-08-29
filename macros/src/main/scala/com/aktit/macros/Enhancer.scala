package com.aktit.macros

import java.io.File

import com.aktit.macros.model.Package

import scala.meta._

/**
  * http://scalameta.org/tutorial/
  *
  * https://github.com/scalameta/scalameta/blob/master/notes/quasiquotes.md
  *
  * @author kostas.kougios
  *         Date: 21/08/17
  */
class Enhancer(files: Seq[File])
{
  def enhance = files.map(enhanceFile)

  private def enhanceFile(file: File) = {
    val source = scala.io.Source.fromFile(file, "UTF-8").mkString

    // see https://docs.scala-lang.org/overviews/quasiquotes/syntax-summary.html
    source.parse[Source] match {
      case Parsed.Success(tree) =>
        tree.children map (extract)
      case Parsed.Error(_, _, details) =>
        throw details
    }
  }

  def extract(child: Tree) = child match {
    case q"$mods trait $tpname[..$tparams] extends { ..$earlydefns } with ..$parents { $self => ..$stats }" =>
      ???
    case q"package $ref { ..$topstats }" =>
      new Package(ref, topstats)
    case _ => throw new IllegalArgumentException(s"can't recognize\n$child")
  }
}
